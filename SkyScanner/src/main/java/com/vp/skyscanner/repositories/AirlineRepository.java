package com.vp.skyscanner.repositories;

import com.vp.skyscanner.models.Airline;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {

  Optional<Airline> findByName(String name);

  Optional<Airline> findById(Long id);
}
