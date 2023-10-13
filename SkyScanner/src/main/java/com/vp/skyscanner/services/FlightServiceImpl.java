package com.vp.skyscanner.services;

import com.vp.skyscanner.dtos.FlightDto;
import com.vp.skyscanner.dtos.FlightSearchResponseDto;
import com.vp.skyscanner.dtos.RequestDto;
import com.vp.skyscanner.models.Flight;
import com.vp.skyscanner.repositories.FlightRepository;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightServiceImpl implements FlightService {

  private final FlightRepository flightRepository;

  @Autowired
  public FlightServiceImpl(FlightRepository flightRepository) {
    this.flightRepository = flightRepository;
  }

  @Override
  public FlightDto convertFlightToDto(Flight flight) {
    return new FlightDto(flight.getId(), flight.getFlightNumber(), flight.getDeparture(),
        flight.getArrive(),
        flight.getTimeOfFlight(), flight.getTimeOfArrive(),
        flight.getAvailableSeats(), flight.getPrice(), flight.getAirline().getName(),
        flight.getAirline().getRating());
  }

  @Override
  public List<FlightDto> getAllFlights() {
    List<Flight> flights = flightRepository.findAll();
    List<FlightDto> flightDtos = new ArrayList<>();
    for (Flight flight : flights) {
      flightDtos.add(convertFlightToDto(flight));
    }
    return flightDtos;
  }

  @Override
  public List<FlightDto> getAllFlightsBasedOnFromAndTo(RequestDto requestDto) {
    List<FlightDto> flightDtos = new ArrayList<>();
    List<Flight> flights = flightRepository.findAll();
    for (Flight flight : flights) {
      if (flight.getDeparture().equals(requestDto.getFrom().trim())
          && flight.getArrive().equals(requestDto.getTo().trim())
          && flight.getAvailableSeats() >= requestDto.getTravellers()) {
        flightDtos.add(convertFlightToDto(flight));
      }
    }
    return flightDtos;
  }

  @Override
  public FlightSearchResponseDto getAllFlightsBasedOnRequest(RequestDto requestDto) {
    List<FlightDto> flights = getAllFlightsBasedOnFromAndTo(requestDto);

    FlightDto cheapestFlight = findCheapestFlight(flights);
    FlightDto fastestFlight = findFastestFlight(flights);

    FlightSearchResponseDto response = new FlightSearchResponseDto();
    response.setCheapestFlight(cheapestFlight);
    response.setFastestFlight(fastestFlight);
    response.setAllFlights(flights);

    return response;
  }

  private FlightDto findCheapestFlight(List<FlightDto> flights) {
    return flights.stream()
        .min(Comparator.comparingInt(FlightDto::getPrice))
        .orElse(null);
  }

  private FlightDto findFastestFlight(List<FlightDto> flights) {
    return flights.stream()
        .min(Comparator.comparingLong(flightDto -> Duration.between(
            flightDto.getTimeOfFlight(), flightDto.getTimeOfArrive()).toMinutes()))
        .orElse(null);
  }
}