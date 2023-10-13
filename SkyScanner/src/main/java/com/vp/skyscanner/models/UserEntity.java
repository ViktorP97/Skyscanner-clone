package com.vp.skyscanner.models;

import com.vp.skyscanner.security.RoleType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String username;
  private String password;
  private String email;
  @Enumerated(EnumType.STRING)
  private RoleType roleType;
  @Column(nullable = false)
  private boolean verified;
  @Column(nullable = false)
  private int money;
  private int flyPoint;
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Ticket> tickets = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Baggage> baggage;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<PriceAlertEntity> priceAlerts = new ArrayList<>(); // Add this line

  public UserEntity() {
    this.money = 0;
    this.flyPoint = 0;
  }

  public UserEntity(String username, String password, String email, RoleType roleType) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.roleType = roleType;
    this.money = 0;
    this.flyPoint = 0;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public RoleType getRoleType() {
    return roleType;
  }

  public void setRoleType(RoleType roleType) {
    this.roleType = roleType;
  }

  public int getMoney() {
    return money;
  }

  public void setMoney(int money) {
    this.money = money;
  }

  public int getFlyPoint() {
    return flyPoint;
  }

  public void setFlyPoint(int flyPoint) {
    this.flyPoint = flyPoint;
  }

  public List<Ticket> getTickets() {
    return tickets;
  }

  public void setTickets(List<Ticket> tickets) {
    this.tickets = tickets;
  }

  public List<Baggage> getBaggage() {
    return baggage;
  }

  public void setBaggage(List<Baggage> baggage) {
    this.baggage = baggage;
  }

  public List<PriceAlertEntity> getPriceAlerts() {
    return priceAlerts;
  }

  public void setPriceAlerts(List<PriceAlertEntity> priceAlerts) {
    this.priceAlerts = priceAlerts;
  }

  public boolean isVerified() {
    return verified;
  }

  public void setVerified(boolean verified) {
    this.verified = verified;
  }

  public void verifyUser() {
    verified = true;
  }
}