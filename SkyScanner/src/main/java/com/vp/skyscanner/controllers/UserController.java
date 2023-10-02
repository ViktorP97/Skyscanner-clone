package com.vp.skyscanner.controllers;

import com.vp.skyscanner.dtos.MoneyDto;
import com.vp.skyscanner.dtos.PreferencesDto;
import com.vp.skyscanner.dtos.ProfileDto;
import com.vp.skyscanner.dtos.TicketDto;
import com.vp.skyscanner.exceptions.UserNameNotFoundException;
import com.vp.skyscanner.models.UserEntity;
import com.vp.skyscanner.repositories.UserRepository;
import com.vp.skyscanner.services.TicketService;
import com.vp.skyscanner.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;
  private final UserRepository userRepository;
  private final TicketService ticketService;

  @Autowired
  public UserController(UserService userService, UserRepository userRepository,
      TicketService ticketService) {
    this.userService = userService;
    this.userRepository = userRepository;
    this.ticketService = ticketService;
  }

  @PostMapping("/add/money")
  public ResponseEntity addMoneyToUser(@RequestBody MoneyDto moneyDto,
      Authentication authentication) {
    String username = authentication.getName();
    UserEntity user = userService.getUserByName(username);
    userService.addMoney(user, moneyDto.getAmount());
    userRepository.save(user);
    return ResponseEntity.ok("Added " + moneyDto.getAmount() + " dollars.");
  }

  @GetMapping("/mytickets")
  public ResponseEntity showMyTickets(Authentication authentication) {
    String username = authentication.getName();
    UserEntity user = userService.getUserByName(username);
    List<TicketDto> ticketDtos = ticketService.showAllTicketsOfTheUser(user);
    if (ticketDtos.isEmpty()) {
      return ResponseEntity.ok("You dont have any tickets");
    }
    return ResponseEntity.ok(ticketDtos);
  }

  @GetMapping("/profile")
  public ResponseEntity<ProfileDto> getProfile(Authentication authentication) {
    try {
      String username = authentication.getName();
      UserEntity user = userService.getUserByName(username);
      if (user == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
      ProfileDto profileDto = userService.showProfile(username);
      return ResponseEntity.ok(profileDto);
    } catch (UserNameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/add/alert")
  public ResponseEntity addPriceAlert(@RequestBody PreferencesDto preferencesDto,
      Authentication authentication) {
    String username = authentication.getName();
    UserEntity user = userService.getUserByName(username);
    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(userService.addPriceAlert(user, preferencesDto));
  }
}
