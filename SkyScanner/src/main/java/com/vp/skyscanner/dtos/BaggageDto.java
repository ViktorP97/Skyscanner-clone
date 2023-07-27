package com.vp.skyscanner.dtos;

public class BaggageDto {

  private int weight;

  private int amount;

  public BaggageDto() {

  }

  public BaggageDto(int weight, int amount) {
    this.weight = weight;
    this.amount = amount;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }
}
