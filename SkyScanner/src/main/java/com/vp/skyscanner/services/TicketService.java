package com.vp.skyscanner.services;

import com.vp.skyscanner.dtos.TicketDto;
import com.vp.skyscanner.models.Ticket;
import com.vp.skyscanner.models.UserEntity;
import java.util.List;

public interface TicketService {

  String buyTickets(Long flightId, int amount, UserEntity user);

  List<TicketDto> showAllTicketsOfTheUser(UserEntity user);

  TicketDto convertTicketToDto(Ticket ticket);
}
