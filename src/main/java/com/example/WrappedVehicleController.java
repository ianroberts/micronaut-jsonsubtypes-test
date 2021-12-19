package com.example;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/wrapped")
public class WrappedVehicleController {

  @Get("/car")
  public Wrapper car() {
    return new Wrapper(new Car(4));
  }

  @Get("/van")
  public Wrapper van() {
    return new Wrapper(new Van(3.5));
  }
}
