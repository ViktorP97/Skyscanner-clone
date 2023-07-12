package com.vp.skyscanner.dtos;

import java.time.LocalDateTime;

public class TicketDto {

  private Long id;
  private String departure;
  private String arrive;
  private LocalDateTime timeOfFlight;
  private LocalDateTime timeOfArrive;

  public TicketDto() {

  }

  public TicketDto(Long id, String departure, String arrive, LocalDateTime timeOfFlight,
      LocalDateTime timeOfArrive) {
    this.id = id;
    this.departure = departure;
    this.arrive = arrive;
    this.timeOfFlight = timeOfFlight;
    this.timeOfArrive = timeOfArrive;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
}
