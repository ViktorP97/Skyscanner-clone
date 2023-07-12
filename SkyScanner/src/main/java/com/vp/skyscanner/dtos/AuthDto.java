package com.vp.skyscanner.dtos;

public class AuthDto {

  String token;

  public AuthDto() {

  }
  public AuthDto(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
