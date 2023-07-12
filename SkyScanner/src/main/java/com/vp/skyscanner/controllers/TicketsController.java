package com.vp.skyscanner.controllers;

import com.vp.skyscanner.dtos.NumberOfTicketsDto;
import com.vp.skyscanner.models.UserEntity;
import com.vp.skyscanner.services.TicketService;
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
public class TicketsController {

  private final UserService userService;
  private final TicketService ticketService;

  @Autowired
  public TicketsController(UserService userService, TicketService ticketService) {
    this.userService = userService;
    this.ticketService = ticketService;
  }

  @PostMapping("/buy/ticket/{id}")
  public ResponseEntity buyTickets(@PathVariable Long id, Authentication authentication,
      @RequestBody
      NumberOfTicketsDto numberOfTicketsDto) {
    try {
      String username = authentication.getName();
      UserEntity user = userService.getUserByName(username);
      return ResponseEntity.ok(ticketService.buyTickets(id, numberOfTicketsDto.getAmount(), user));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
