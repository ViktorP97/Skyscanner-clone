package com.vp.skyscanner.dtos;

import java.util.List;

public class FlightSearchResponseDto {

  private FlightDto cheapestFlight;

  private FlightDto fastestFlight;

  private List<FlightDto> allFlights;

  public FlightSearchResponseDto() {

  }

  public FlightSearchResponseDto(FlightDto cheapestFlight, FlightDto fastestFlight,
      List<FlightDto> allFlights) {
    this.cheapestFlight = cheapestFlight;
    this.fastestFlight = fastestFlight;
    this.allFlights = allFlights;
  }

  public FlightDto getCheapestFlight() {
    return cheapestFlight;
  }

  public void setCheapestFlight(FlightDto cheapestFlight) {
    this.cheapestFlight = cheapestFlight;
  }

  public FlightDto getFastestFlight() {
    return fastestFlight;
  }

  public void setFastestFlight(FlightDto fastestFlight) {
    this.fastestFlight = fastestFlight;
  }

  public List<FlightDto> getAllFlights() {
    return allFlights;
  }

  public void setAllFlights(List<FlightDto> allFlights) {
    this.allFlights = allFlights;
  }
}
