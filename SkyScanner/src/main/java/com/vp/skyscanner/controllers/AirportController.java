package com.vp.skyscanner.controllers;

import com.vp.skyscanner.models.UserEntity;
import com.vp.skyscanner.services.AirportService;
import com.vp.skyscanner.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AirportController {

  private final UserService userService;
  private final AirportService airportService;

  @Autowired
  public AirportController(UserService userService, AirportService airportService) {
    this.userService = userService;
    this.airportService = airportService;
  }

  @PostMapping("/airport/{id}")
  public ResponseEntity<String> useTicket(@PathVariable Long id, Authentication authentication) {
    try {
      String username = authentication.getName();
      UserEntity user = userService.getUserByName(username);
      if (user == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
      }
      String result = airportService.useTicket(id, user);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
