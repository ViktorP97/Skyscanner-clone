package com.vp.skyscanner.exceptions;

public class PasswordsDontMatchException extends RuntimeException {

  public PasswordsDontMatchException(String message) {
    super(message);
  }

}
