package com.vp.skyscanner.models;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tickets")
public class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String departure;
  private String arrive;
  private LocalDateTime timeOfFlight;
  private LocalDateTime timeOfArrive;
  private boolean used;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "flight_id")
  private Flight flight;

  public Ticket() {
    this.used = false;
  }

  public Ticket(String departure, String arrive, LocalDateTime timeOfFlight,
      LocalDateTime timeOfArrive) {
    this.departure = departure;
    this.arrive = arrive;
    this.timeOfFlight = timeOfFlight;
    this.timeOfArrive = timeOfArrive;
    this.used = false;
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

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public Flight getFlight() {
    return flight;
  }

  public void setFlight(Flight flight) {
    this.flight = flight;
  }

  public boolean isUsed() {
    return used;
  }

  public void setUsed(boolean used) {
    this.used = used;
  }
}