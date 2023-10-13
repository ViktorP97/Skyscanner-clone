package com.vp.skyscanner.services;

import com.vp.skyscanner.dtos.AirlineDto;
import com.vp.skyscanner.dtos.RatingDto;
import java.util.List;

public interface AirlineService {

  List<AirlineDto> showAllAirlines();

  String addRating(Long airlineId, RatingDto ratingDto);
}
