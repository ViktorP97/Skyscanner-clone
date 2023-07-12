package com.vp.skyscanner.services;

import com.vp.skyscanner.dtos.TicketDto;
import com.vp.skyscanner.models.Flight;
import com.vp.skyscanner.models.Ticket;
import com.vp.skyscanner.models.UserEntity;
import com.vp.skyscanner.repositories.FlightRepository;
import com.vp.skyscanner.repositories.TicketRepository;
import com.vp.skyscanner.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

  private final FlightRepository flightRepository;
  private final UserRepository userRepository;
  private final TicketRepository ticketRepository;

  @Autowired
  public TicketServiceImpl(FlightRepository flightRepository, UserRepository userRepository,
      TicketRepository ticketRepository) {
    this.flightRepository = flightRepository;
    this.userRepository = userRepository;
    this.ticketRepository = ticketRepository;
  }

  @Override
  public String buyTickets(Long flightId, int amount, UserEntity user) {
    Optional<Flight> flightOptional = flightRepository.findById(flightId);
    if (!flightOptional.isPresent()) {
      return "Flight not found";
    }
    Flight flight = flightOptional.get();
    if (flight.getAvailableSeats() < amount) {
      return "Not enough seats";
    }
    if (user.getMoney() < flight.getPrice() * amount) {
      return "Not enough money";
    }

    List<Ticket> tickets = new ArrayList<>();
    for (int i = 0; i < amount; i++) {
      Ticket ticket = new Ticket(flight.getDeparture(),
          flight.getArrive(), flight.getTimeOfFlight(),
          flight.getTimeOfArrive());
      ticket.setFlight(flight);
      ticket.setUser(user);
      user.getTickets().add(ticket);
      tickets.add(ticket);
    }

    flight.setAvailableSeats(flight.getAvailableSeats() - amount);
    user.setMoney(user.getMoney() - flight.getPrice() * amount);

    flightRepository.save(flight);
    userRepository.save(user);
    ticketRepository.saveAll(tickets);

    return "Tickets successfully bought";
  }

  @Override
  public List<TicketDto> showAllTicketsOfTheUser(UserEntity user) {
    List<TicketDto> ticketDtos = new ArrayList<>();
    List<Ticket> tickets = user.getTickets();
    for (Ticket ticket : tickets) {
      ticketDtos.add(convertTicketToDto(ticket));
    }
    return ticketDtos;
  }

  @Override
  public TicketDto convertTicketToDto(Ticket ticket) {
    return new TicketDto(ticket.getId(), ticket.getDeparture(), ticket.getArrive(),
        ticket.getTimeOfFlight(), ticket.getTimeOfArrive());
  }
}
