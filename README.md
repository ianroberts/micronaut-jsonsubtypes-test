# Incomplete handling of `@JsonSubTypes` in Micronaut native image

This app demostrates an issue with the handling of `@JsonSubTypes` in native images under Micronaut 3.2.3.  The app defines a polymorphic class hierarchy that uses `@JsonSubTypes` to declare a property discriminator

```java
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "car", value = Car.class),
        @JsonSubTypes.Type(name = "van", value = Van.class),
})
@Introspected
@Data
public class Vehicle {
  private int wheels = 4;
}
```

So the `Car` subclass serializes as `{"type":"car", ...}` and the `Van` subclass as `{"type":"van", ...}`.

It then defines two controllers that return `Vehicle` instances as JSON, one with the vehicle as the root object:

```java
  @Get("/car")
  public Vehicle car() { return new Car(4); }

  @Get("/van")
  public Vehicle van() { return new Van(3.5); }
```

and the other with the vehicle as a property of a different top-level object:
```java
@Introspected
@Data
@AllArgsConstructor
public class Wrapper {
  private Vehicle vehicle;
}
```

```java
  @Get("/car")
  public Wrapper car() { return new Wrapper(new Car(4)); }

  @Get("/van")
  public Wrapper van() { return new Wrapper(new Van(3.5)); }
```

When running under a regular JVM these serialize correctly in both cases, but when running as a native image only the _unwrapped_ version (where the controller methods return `Vehicle`) work correctly - the `Wrapper` version omits the `type` discriminator property on the nested vehicle.
