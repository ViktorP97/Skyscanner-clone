package com.vp.skyscanner.services;

import com.vp.skyscanner.models.UserEntity;

public interface BaggageService {

  String buyBaggage(long flightId, double weight, int amount, UserEntity user);
}
