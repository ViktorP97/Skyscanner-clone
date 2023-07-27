package com.vp.skyscanner.repositories;

import com.vp.skyscanner.models.Baggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaggageRepository extends JpaRepository<Baggage, Long> {

}
