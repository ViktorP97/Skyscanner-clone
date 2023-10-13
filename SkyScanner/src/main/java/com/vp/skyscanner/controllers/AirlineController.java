package com.vp.skyscanner.controllers;

import com.vp.skyscanner.dtos.AirlineDto;
import com.vp.skyscanner.dtos.RatingDto;
import com.vp.skyscanner.services.AirlineService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AirlineController {

  private final AirlineService airlineService;

  @Autowired
  public AirlineController(AirlineService airlineService) {
    this.airlineService = airlineService;
  }

  @GetMapping("/airlines")
  public ResponseEntity<List<AirlineDto>> getAllAirlines() {
    List<AirlineDto> airlines = airlineService.showAllAirlines();
    return ResponseEntity.ok(airlines);
  }

  @PostMapping("/airline/rating/{id}")
  public ResponseEntity addRatingToAirline(@PathVariable long id, @RequestBody RatingDto ratingDto) {
    return ResponseEntity.ok(airlineService.addRating(id, ratingDto));
  }
}
