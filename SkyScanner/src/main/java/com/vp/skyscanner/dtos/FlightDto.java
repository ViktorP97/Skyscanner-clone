package com.vp.skyscanner.dtos;

import java.time.LocalDateTime;

public class FlightDto {

  private Long id;
  private int flightNumber;
  private String departure;
  private String arrive;
  private LocalDateTime timeOfFlight;
  private LocalDateTime timeOfArrive;
  private int availableSeats;
  private int price;
  private String airline;
  private double airlineRating;

  public FlightDto() {

  }

  public FlightDto(Long id, int flightNumber, String departure, String arrive,
      LocalDateTime timeOfFlight,
      LocalDateTime timeOfArrive, int availableSeats, int price, String airline, double airlineRating) {
    this.id = id;
    this.flightNumber = flightNumber;
    this.departure = departure;
    this.arrive = arrive;
    this.timeOfFlight = timeOfFlight;
    this.timeOfArrive = timeOfArrive;
    this.availableSeats = availableSeats;
    this.price = price;
    this.airline = airline;
    this.airlineRating = airlineRating;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getFlightNumber() {
    return flightNumber;
  }

  public void setFlightNumber(int flightNumber) {
    this.flightNumber = flightNumber;
  }

  public String getDeparture() {
    return departure;
  }

  public void setDeparture(String departure) {
    this.departure = departure;
  }

  public String getArrive() {
    return arrive;
  }

  public void setArrive(String arrive) {
    this.arrive = arrive;
  }

  public LocalDateTime getTimeOfFlight() {
    return timeOfFlight;
  }

  public void setTimeOfFlight(LocalDateTime timeOfFlight) {
    this.timeOfFlight = timeOfFlight;
  }

  public LocalDateTime getTimeOfArrive() {
    return timeOfArrive;
  }

  public void setTimeOfArrive(LocalDateTime timeOfArrive) {
    this.timeOfArrive = timeOfArrive;
  }

  public int getAvailableSeats() {
    return availableSeats;
  }

  public void setAvailableSeats(int availableSeats) {
    this.availableSeats = availableSeats;
  }

  public String getAirline() {
    return airline;
  }

  public void setAirline(String airline) {
    this.airline = airline;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public double getAirlineRating() {
    return airlineRating;
  }

  public void setAirlineRating(double airlineRating) {
    this.airlineRating = airlineRating;
  }
}
