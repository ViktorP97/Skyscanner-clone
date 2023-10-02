package com.vp.skyscanner.controllers;

import com.vp.skyscanner.dtos.AuthDto;
import com.vp.skyscanner.dtos.LoginDto;
import com.vp.skyscanner.dtos.PasswordDto;
import com.vp.skyscanner.dtos.RegisterDto;
import com.vp.skyscanner.models.UserEntity;
import com.vp.skyscanner.services.RegistrationService;
import com.vp.skyscanner.services.UserService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserService userService;

  private final RegistrationService registrationService;

  @Autowired
  public AuthController(UserService userService, RegistrationService registrationService) {
    this.userService = userService;
    this.registrationService = registrationService;
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
    return ResponseEntity.ok(registrationService.register(registerDto));
  }

  @GetMapping("/confirm")
  public ResponseEntity<String> confirmRegistration(@RequestParam String token) {
    UserEntity user = registrationService.confirmRegistration(token);

    return new ResponseEntity<>(
        "Your account has been successfully verified " + user.getUsername()
            + ". You can now log in.",
        HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody LoginDto loginDto) {
    try {
      AuthDto authDto = registrationService.login(loginDto);
      return ResponseEntity.ok(authDto);
    } catch (BadCredentialsException e) {
      // Handle the BadCredentialsException here and return a customized response
      String errorMessage = e.getMessage();
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }
  }

  @PostMapping("/change")
  public ResponseEntity changePassword(@RequestBody PasswordDto passwordDto,
      Authentication authentication) {
    String username = authentication.getName();
    UserEntity user = userService.getUserByName(username);
    return ResponseEntity.ok(userService.changePassword(user, passwordDto));
  }
}
