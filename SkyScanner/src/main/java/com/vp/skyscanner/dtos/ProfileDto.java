package com.vp.skyscanner.dtos;

import java.util.List;

public class ProfileDto {

  private int flyPoints;
  private List<TicketDto> history;
  private List<TicketDto> myTickets;

  public ProfileDto() {

  }

  public ProfileDto(int flyPoints, List<TicketDto> history, List<TicketDto> myTickets) {
    this.flyPoints = flyPoints;
    this.history = history;
    this.myTickets = myTickets;
  }

  public int getFlyPoints() {
    return flyPoints;
  }

  public void setFlyPoints(int flyPoints) {
    this.flyPoints = flyPoints;
  }

  public List<TicketDto> getHistory() {
    return history;
  }

  public void setHistory(List<TicketDto> history) {
    this.history = history;
  }

  public List<TicketDto> getMyTickets() {
    return myTickets;
  }

  public void setMyTickets(List<TicketDto> myTickets) {
    this.myTickets = myTickets;
  }
}
