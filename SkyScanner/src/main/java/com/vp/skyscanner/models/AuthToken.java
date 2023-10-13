package com.vp.skyscanner.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "auth_tokens")
public class AuthToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String tokenValue;

  private boolean valid;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @Column(nullable = false)
  private long expirationDate;

  public AuthToken() {

  }

  public AuthToken(String tokenValue, UserEntity user, long expirationDate) {
    this.tokenValue = tokenValue;
    this.user = user;
    this.expirationDate = expirationDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTokenValue() {
    return tokenValue;
  }

  public void setTokenValue(String tokenValue) {
    this.tokenValue = tokenValue;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public long getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(long expirationDate) {
    this.expirationDate = expirationDate;
  }

  public boolean isValid() {
    return valid;
  }

  public void setValid(boolean valid) {
    this.valid = valid;
  }
}
