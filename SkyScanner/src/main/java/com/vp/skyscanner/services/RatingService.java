package com.vp.skyscanner.services;

import com.vp.skyscanner.dtos.RatingDto;

public interface RatingService {

  String addRating(Long airlineId, RatingDto ratingDto);
}
