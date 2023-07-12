package com.vp.skyscanner.services;

import com.vp.skyscanner.dtos.AirlineDto;
import com.vp.skyscanner.models.Airline;
import com.vp.skyscanner.repositories.AirlineRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirlineServiceImpl implements AirlineService {

  private final AirlineRepository airlineRepository;

  @Autowired
  public AirlineServiceImpl(AirlineRepository airlineRepository) {
    this.airlineRepository = airlineRepository;
  }

  @Override
  public List<AirlineDto> showAllAirlines() {
    List<Airline> airlines = airlineRepository.findAll();
    List<AirlineDto> airlineDtos = new ArrayList<>();
    for (Airline airline : airlines) {
      airlineDtos.add(new AirlineDto(airline.getName()));
    }
    return airlineDtos;
  }
}
