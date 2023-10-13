package com.vp.skyscanner.services;

import com.vp.skyscanner.dtos.PasswordDto;
import com.vp.skyscanner.dtos.PreferencesDto;
import com.vp.skyscanner.dtos.ProfileDto;
import com.vp.skyscanner.dtos.TicketDto;
import com.vp.skyscanner.exceptions.UserNameNotFoundException;
import com.vp.skyscanner.models.PriceAlertEntity;
import com.vp.skyscanner.models.Ticket;
import com.vp.skyscanner.models.UserEntity;
import com.vp.skyscanner.repositories.PriceAlertEntityRepository;
import com.vp.skyscanner.repositories.UserRepository;
import com.vp.skyscanner.security.JwtService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  private final PriceAlertEntityRepository priceAlertEntityRepository;

  private final TicketService ticketService;

  private final PriceAlertEntityService priceAlertEntityService;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
      JwtService jwtService, AuthenticationManager authenticationManager,
      PriceAlertEntityRepository priceAlertEntityRepository, TicketService ticketService,
      PriceAlertEntityService priceAlertEntityService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.priceAlertEntityRepository = priceAlertEntityRepository;
    this.ticketService = ticketService;
    this.priceAlertEntityService = priceAlertEntityService;
  }

  @Override
  public void createUser(UserEntity user) {
    userRepository.save(user);
  }

  @Override
  public Boolean usernameExists(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public Boolean emailExists(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public UserEntity getUserByName(String playerName) throws UserNameNotFoundException {
    Optional<UserEntity> user = userRepository.findByUsername(playerName);
    if (user.isPresent()) {
      return user.get();
    } else {
      throw new UserNameNotFoundException("Player name not found: " + playerName);
    }
  }

  @Override
  public String changePassword(UserEntity user, PasswordDto passwordDto) {
    if (!passwordDto.getPassword().equals(passwordDto.getRepeatPassword())) {
      return "Passwords dont match";
    }
    user.setPassword(passwordDto.getPassword());
    userRepository.save(user);
    return "Password successfully changed";
  }

  @Override
  public ProfileDto showProfile(String username) {
    Optional<UserEntity> userEntityOptional = userRepository.findByUsername(username);
    if (!userEntityOptional.isPresent()) {
      throw new UserNameNotFoundException("User not found");
    }
    UserEntity user = userEntityOptional.get();
    List<Ticket> tickets = user.getTickets();
    List<TicketDto> ticketDtos = new ArrayList<>();
    List<TicketDto> usedTicketsDto = new ArrayList<>();
    for (Ticket ticket : tickets) {
      if (ticket.isUsed()) {
        usedTicketsDto.add(ticketService.convertTicketToDto(ticket));
      } else {
        ticketDtos.add(ticketService.convertTicketToDto(ticket));
      }
    }
    return new ProfileDto(user.getFlyPoint(), usedTicketsDto, ticketDtos);
  }

  @Override
  public void addMoney(UserEntity user, int money) {
    user.setMoney(money);
  }

  @Override
  public void addUser(UserEntity user) {
    userRepository.save(user);
  }

  @Override
  public String addPriceAlert(UserEntity user, PreferencesDto preferencesDto) {
    PriceAlertEntity priceAlert = new PriceAlertEntity(preferencesDto.getDeparture(), preferencesDto.getDestination(), preferencesDto.getPrice());
    priceAlert.setUser(user);
    priceAlertEntityRepository.save(priceAlert);
    userRepository.save(user);
    priceAlertEntityService.alertFlight();
    return "Price alert added";
  }
}
