package com.vp.skyscanner.dtos;

public class PasswordDto {

  private String password;
  private String repeatPassword;

  public PasswordDto() {

  }

  public PasswordDto(String password, String repeatPassword) {
    this.password = password;
    this.repeatPassword = repeatPassword;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRepeatPassword() {
    return repeatPassword;
  }

  public void setRepeatPassword(String repeatPassword) {
    this.repeatPassword = repeatPassword;
  }
}
