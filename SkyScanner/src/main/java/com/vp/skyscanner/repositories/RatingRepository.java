package com.vp.skyscanner.repositories;

import com.vp.skyscanner.models.Airline;
import com.vp.skyscanner.models.Rating;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

  List<Rating> findAllByAirline(Airline airline);
}
