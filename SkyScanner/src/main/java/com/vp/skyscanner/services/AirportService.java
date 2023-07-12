package com.vp.skyscanner.services;

import com.vp.skyscanner.models.UserEntity;

public interface AirportService {

  String useTicket(Long ticketId, UserEntity user);
}