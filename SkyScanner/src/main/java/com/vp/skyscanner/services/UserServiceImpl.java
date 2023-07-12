package com.vp.skyscanner.services;

import com.vp.skyscanner.dtos.AuthDto;
import com.vp.skyscanner.dtos.LoginDto;
import com.vp.skyscanner.dtos.PasswordDto;
import com.vp.skyscanner.dtos.ProfileDto;
import com.vp.skyscanner.dtos.RegisterDto;
import com.vp.skyscanner.dtos.TicketDto;
import com.vp.skyscanner.exceptions.SignatureException;
import com.vp.skyscanner.exceptions.UserNameNotFoundException;
import com.vp.skyscanner.models.Ticket;
import com.vp.skyscanner.models.UserEntity;
import com.vp.skyscanner.repositories.UserRepository;
import com.vp.skyscanner.security.JwtService;
import com.vp.skyscanner.security.RoleType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  private final TicketService ticketService;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
      JwtService jwtService, AuthenticationManager authenticationManager,
      TicketService ticketService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.ticketService = ticketService;
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
  public String register(RegisterDto registerDto) {

    if (usernameExists(registerDto.getUsername())) {
      return "Username already taken";
    }
    if (emailExists(registerDto.getEmail())) {
      return "Email already in use";
    }
    if (!containDigit(registerDto.getPassword())) {
      return "Password must contain at least one digit";
    }
    if (!containBigLetter(registerDto.getPassword())) {
      return "Password must contain at least one big letter";
    }
    if (!passwordIsLongEnough(registerDto.getPassword())) {
      return "Password must be at least 10 characters long";
    }
    UserEntity user = new UserEntity();
    user.setUsername(registerDto.getUsername());
    user.setEmail(registerDto.getEmail());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
    user.setRoleType(RoleType.USER);
    userRepository.save(user);

    return "User registered";
  }

  public boolean containDigit(String password) {
    for (Character character : password.toCharArray()) {
      if (Character.isDigit(character)) {
        return true;
      }
    }
    return false;
  }

  public boolean containBigLetter(String password) {
    for (Character character : password.toCharArray()) {
      if (Character.isUpperCase(character)) {
        return true;
      }
    }
    return false;
  }

  public boolean passwordIsLongEnough(String password) {
    return password.length() >= 10;
  }

  @Override
  public AuthDto login(LoginDto loginDto) {
    UserEntity user = userRepository.findByUsername(loginDto.getUsername())
        .orElseThrow(() -> new SignatureException("Invalid username or password"));

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

    String jwtToken = jwtService.generateToken(user);

    AuthDto authDto = new AuthDto();
    authDto.setToken(jwtToken);

    return authDto;
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
}
