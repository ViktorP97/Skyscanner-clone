package com.vp.skyscanner.dtos;

public class AirlineDto {

  String name;

  public AirlineDto() {

  }
  public AirlineDto(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
