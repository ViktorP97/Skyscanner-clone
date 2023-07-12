package com.vp.skyscanner.dtos;

public class MoneyDto {

  private int amount;

  public MoneyDto() {

  }

  public MoneyDto(int amount) {
    this.amount = amount;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }
}