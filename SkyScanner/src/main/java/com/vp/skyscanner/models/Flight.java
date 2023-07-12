package com.vp.skyscanner.models;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "flights")
public class Flight {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private int flightNumber;
  private String departure;
  private String arrive;
  private LocalDateTime timeOfFlight;
  private LocalDateTime timeOfArrive;
  private int availableSeats;
  private int price;
  @ManyToOne
  @JoinColumn(name = "airline_id")
  private Airline airline;

  @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Ticket> tickets = new ArrayList<>();

  public Flight() {

  }

  public Flight(int flightNumber, String departure, String arrive, int availableSeats,
      LocalDateTime timeOfFlight, LocalDateTime timeOfArrive, int price) {
    this.flightNumber = flightNumber;
    this.departure = departure;
    this.arrive = arrive;
    this.availableSeats = availableSeats;
    this.timeOfArrive = timeOfArrive;
    this.timeOfFlight = timeOfFlight;
    this.price = price;
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

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public Airline getAirline() {
    return airline;
  }

  public void setAirline(Airline airline) {
    this.airline = airline;
  }

  public List<Ticket> getTickets() {
    return tickets;
  }

  public void setTickets(List<Ticket> tickets) {
    this.tickets = tickets;
  }
}