package com.vp.skyscanner.services;

import com.vp.skyscanner.dtos.AuthDto;
import com.vp.skyscanner.dtos.LoginDto;
import com.vp.skyscanner.dtos.RegisterDto;
import com.vp.skyscanner.models.UserEntity;

public interface RegistrationService {

  String register(RegisterDto registerDto);

  UserEntity confirmRegistration(String token);

  AuthDto login(LoginDto loginDto);

  String logout(UserEntity user);
}

