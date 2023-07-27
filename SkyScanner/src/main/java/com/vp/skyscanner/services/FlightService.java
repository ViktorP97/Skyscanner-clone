package com.vp.skyscanner.services;

import com.vp.skyscanner.dtos.FlightDto;
import com.vp.skyscanner.dtos.FlightSearchResponseDto;
import com.vp.skyscanner.dtos.RequestDto;
import com.vp.skyscanner.models.Flight;
import java.util.List;

public interface FlightService {

  FlightDto convertFlightToDto(Flight flight);

  List<FlightDto> getAllFlights();

  List<FlightDto> getAllFlightsBasedOnFromAndTo(RequestDto requestDto);

  FlightSearchResponseDto getAllFlightsBasedOnRequest(RequestDto requestDto);
}