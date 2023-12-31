package com.vp.skyscanner.services;

import com.vp.skyscanner.models.ConfirmationToken;
import com.vp.skyscanner.repositories.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

  private final ConfirmationTokenRepository confirmationTokenRepository;

  @Autowired
  public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
    this.confirmationTokenRepository = confirmationTokenRepository;
  }

  @Override
  public ConfirmationToken saveConfirmationToken(ConfirmationToken confirmationToken) {
    return confirmationTokenRepository.save(confirmationToken);
  }

  @Override
  public ConfirmationToken getConfirmationToken(String token) {
    return confirmationTokenRepository.findByToken(token);
  }

  @Override
  public void deleteConfirmationToken(ConfirmationToken confirmationToken) {
    confirmationTokenRepository.delete(confirmationToken);
  }
}
