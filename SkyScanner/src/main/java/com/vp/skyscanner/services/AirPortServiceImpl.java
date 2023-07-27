package com.vp.skyscanner.services;

import com.vp.skyscanner.models.Ticket;
import com.vp.skyscanner.models.UserEntity;
import com.vp.skyscanner.repositories.BaggageRepository;
import com.vp.skyscanner.repositories.TicketRepository;
import com.vp.skyscanner.repositories.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AirPortServiceImpl implements AirportService {

  private final UserRepository userRepository;
  private final TicketRepository ticketRepository;

  private final BaggageRepository baggageRepository;
  public AirPortServiceImpl(UserRepository userRepository, TicketRepository ticketRepository,
      BaggageRepository baggageRepository) {
    this.userRepository = userRepository;
    this.ticketRepository = ticketRepository;
    this.baggageRepository = baggageRepository;
  }

  @Override
  public String useTicket(Long ticketId, UserEntity user) {
    Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
    if (!ticketOptional.isPresent()) {
      return "Ticket not found";
    }
    Ticket ticket = ticketOptional.get();
    if (ticket.isUsed()) {
      return "Ticket already used";
    }
    ticket.setUsed(true);
    user.getTickets().remove(ticket);
    userRepository.save(user);
    return "Enjoy you flight";
  }
}
