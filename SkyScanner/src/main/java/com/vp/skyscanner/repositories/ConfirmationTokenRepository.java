package com.vp.skyscanner.repositories;

import com.vp.skyscanner.models.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

  ConfirmationToken findByToken(String token);
}
