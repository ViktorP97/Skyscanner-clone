package com.vp.skyscanner.exceptions;

public class AirlineNotFoundException extends RuntimeException {

  public AirlineNotFoundException(String message) {
    super(message);
  }

}
