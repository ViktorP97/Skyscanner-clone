package com.vp.skyscanner.services;

import com.vp.skyscanner.dtos.RatingDto;
import com.vp.skyscanner.models.Airline;
import com.vp.skyscanner.models.Rating;
import com.vp.skyscanner.repositories.AirlineRepository;
import com.vp.skyscanner.repositories.RatingRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

  private final AirlineRepository airlineRepository;

  private final RatingRepository ratingRepository;

  @Autowired
  public RatingServiceImpl(AirlineRepository airlineRepository, RatingRepository ratingRepository) {
    this.airlineRepository = airlineRepository;
    this.ratingRepository = ratingRepository;
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
    rating.setAirline(airline);
    rating.setRating(ratingDto.getRating());

    ratingRepository.save(rating);

    List<Rating> ratings = ratingRepository.findAllByAirline(airline);

    double newTotalRating = ratings.stream().mapToDouble(Rating::getRating).sum();

    double newAverageRating = newTotalRating / ratings.size();

    airline.setRating(newAverageRating);

    airline.setRatings(ratings);

    airlineRepository.save(airline);

    return "Rating successfully added";
  }

}
