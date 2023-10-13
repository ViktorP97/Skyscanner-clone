package com.vp.skyscanner.services;

import com.vp.skyscanner.dtos.AirlineDto;
import com.vp.skyscanner.dtos.RatingDto;
import com.vp.skyscanner.models.Airline;
import com.vp.skyscanner.models.Rating;
import com.vp.skyscanner.repositories.AirlineRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
      airlineDtos.add(new AirlineDto(airline.getName(), airline.getRating()));
    }
    return airlineDtos;
  }

  @Override
  public String addRating(Long airlineId, RatingDto ratingDto) {
    Optional<Airline> airlineOptional = airlineRepository.findById(airlineId);
    if (!airlineOptional.isPresent()) {
      return "Airline not found";
    }
    Airline airline = airlineOptional.get();
    if (ratingDto.getRating() < 0 || ratingDto.getRating() > 10) {
      return "The rating must be between 0 and 10";
    }
    Rating rating = new Rating();
    double currentRating = airline.getRating();
    int amountOfRatings = airline.getRatings().size();

    double newTotalRating = currentRating * amountOfRatings + ratingDto.getRating();

    double newAverageRating = newTotalRating / (amountOfRatings + 1);

    airline.setRating(newAverageRating);

    airline.getRatings().add(rating);

    airlineRepository.save(airline);
    return "Rating successfully added";
  }
}
