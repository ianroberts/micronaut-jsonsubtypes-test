package com.example;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/unwrapped")
public class UnwrappedVehicleController {

  @Get("/car")
  public Vehicle car() {
    return new Car(4);
  }

  @Get("/van")
  public Vehicle van() {
    return new Van(3.5);
  }
}
