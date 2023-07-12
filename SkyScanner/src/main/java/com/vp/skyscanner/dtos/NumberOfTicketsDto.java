package com.vp.skyscanner.dtos;

public class NumberOfTicketsDto {

  private int amount;

  public NumberOfTicketsDto() {

  }

  public NumberOfTicketsDto(int amount) {
    this.amount = amount;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }
}
