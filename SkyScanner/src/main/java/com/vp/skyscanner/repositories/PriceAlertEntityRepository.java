package com.vp.skyscanner.repositories;

import com.vp.skyscanner.models.PriceAlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceAlertEntityRepository extends JpaRepository<PriceAlertEntity, Long> {

}
