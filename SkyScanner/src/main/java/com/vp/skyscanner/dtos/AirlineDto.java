package com.vp.skyscanner.dtos;

public class AirlineDto {

  String name;

  private double rating;

  public AirlineDto() {

  }
  public AirlineDto(String name, double rating) {
    this.name = name;
    this.rating = rating;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }
}
