package com.vp.skyscanner.repositories;

import com.vp.skyscanner.models.Flight;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

  Optional<Flight> findById(Long id);
}
