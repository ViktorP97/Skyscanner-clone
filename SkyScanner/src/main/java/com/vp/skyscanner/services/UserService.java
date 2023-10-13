package com.vp.skyscanner.services;

import com.vp.skyscanner.dtos.AuthDto;
import com.vp.skyscanner.dtos.LoginDto;
import com.vp.skyscanner.dtos.PasswordDto;
import com.vp.skyscanner.dtos.PreferencesDto;
import com.vp.skyscanner.dtos.ProfileDto;
import com.vp.skyscanner.dtos.RegisterDto;
import com.vp.skyscanner.exceptions.UserNameNotFoundException;
import com.vp.skyscanner.models.PriceAlertEntity;
import com.vp.skyscanner.models.UserEntity;

public interface UserService {

  void createUser(UserEntity user);

  Boolean usernameExists(String username);

  Boolean emailExists(String email);

  void addMoney(UserEntity user, int money);

  UserEntity getUserByName(String playerName) throws UserNameNotFoundException;

  String changePassword(UserEntity user, PasswordDto passwordDto);

  ProfileDto showProfile(String username);

  void addUser(UserEntity user);

  String addPriceAlert(UserEntity user, PreferencesDto preferencesDto);

}