package com.vp.skyscanner.controllers;

import com.vp.skyscanner.dtos.AuthDto;
import com.vp.skyscanner.dtos.LoginDto;
import com.vp.skyscanner.dtos.PasswordDto;
import com.vp.skyscanner.dtos.RegisterDto;
import com.vp.skyscanner.models.UserEntity;
import com.vp.skyscanner.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserService userService;

  @Autowired
  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
    return ResponseEntity.ok(userService.register(registerDto));
  }

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody LoginDto loginDto) {
    AuthDto authDto = userService.login(loginDto);
    return ResponseEntity.ok(authDto);
  }

  @PostMapping("/change")
  public ResponseEntity changePassword(@RequestBody PasswordDto passwordDto,
      Authentication authentication) {
    String username = authentication.getName();
    UserEntity user = userService.getUserByName(username);
    return ResponseEntity.ok(userService.changePassword(user, passwordDto));
  }
}
