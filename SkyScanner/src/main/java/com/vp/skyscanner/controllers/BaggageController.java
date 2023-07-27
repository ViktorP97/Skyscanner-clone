package com.vp.skyscanner.controllers;

import com.vp.skyscanner.dtos.BaggageDto;
import com.vp.skyscanner.models.UserEntity;
import com.vp.skyscanner.services.BaggageService;
import com.vp.skyscanner.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BaggageController {

  private final UserService userService;

  private final BaggageService baggageService;

  @Autowired
  public BaggageController(UserService userService, BaggageService baggageService) {
    this.userService = userService;
    this.baggageService = baggageService;
  }

  @PostMapping("/buy/baggage/{id}")
  public ResponseEntity buyBaggage(@PathVariable Long id, Authentication authentication,
      @RequestBody BaggageDto baggageDto) {
    try {
      String username = authentication.getName();
      UserEntity user = userService.getUserByName(username);
      return ResponseEntity.ok(
          baggageService.buyBaggage(id, baggageDto.getWeight(), baggageDto.getAmount(), user));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
