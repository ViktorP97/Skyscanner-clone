package com.vp.skyscanner.controllers;

import com.vp.skyscanner.dtos.FlightDto;
import com.vp.skyscanner.dtos.FlightSearchResponse;
import com.vp.skyscanner.dtos.RequestDto;
import com.vp.skyscanner.services.FlightService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FlightsController {

  private final FlightService flightService;

  @Autowired
  public FlightsController(FlightService flightService) {
    this.flightService = flightService;
  }

  @GetMapping("/flights")
  public ResponseEntity<List<FlightDto>> getAllFlights() {
    List<FlightDto> flights = flightService.getAllFlights();
    return ResponseEntity.ok(flights);
  }

  @PostMapping("/tickets")
  public ResponseEntity getAllFlightsBasedOnRequest(@RequestBody RequestDto requestDto) {
    FlightSearchResponse response = flightService.getAllFlightsBasedOnRequest(requestDto);
    if (response.getAllFlights().isEmpty()) {
      return ResponseEntity.status(404).body("No tickets found");
    }
    return ResponseEntity.ok(response);
  }
}
