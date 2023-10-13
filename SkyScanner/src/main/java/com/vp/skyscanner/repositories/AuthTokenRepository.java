package com.vp.skyscanner.repositories;

import com.vp.skyscanner.models.AuthToken;
import com.vp.skyscanner.models.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

  Optional<AuthToken> findByTokenValue(String tokenValue);

  Optional<AuthToken> findByUserAndId(UserEntity user, long id);

  void deleteById(Long id);
}
