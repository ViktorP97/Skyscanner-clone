package com.vp.skyscanner.dtos;

public class PreferencesDto {

  private String departure;

  private String destination;

  private int price;

  public PreferencesDto() {

  }

  public PreferencesDto(String departure, String destination, int price) {
    this.departure = departure;
    this.destination = destination;
    this.price = price;
  }

  public String getDeparture() {
    return departure;
  }

  public void setDeparture(String departure) {
    this.departure = departure;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
