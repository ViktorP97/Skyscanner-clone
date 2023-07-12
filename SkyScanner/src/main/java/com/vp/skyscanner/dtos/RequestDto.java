package com.vp.skyscanner.dtos;

public class RequestDto {

  private String from;
  private String to;
  private int travellers;

  public RequestDto() {

  }

  public RequestDto(String from, String to, int travellers) {
    this.from = from;
    this.to = to;
    this.travellers = travellers;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public int getTravellers() {
    return travellers;
  }

  public void setTravellers(int travellers) {
    this.travellers = travellers;
  }
}