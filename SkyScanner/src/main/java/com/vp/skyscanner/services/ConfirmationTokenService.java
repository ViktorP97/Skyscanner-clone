package com.vp.skyscanner.services;

import com.vp.skyscanner.models.ConfirmationToken;

public interface ConfirmationTokenService {

  ConfirmationToken saveConfirmationToken(ConfirmationToken token);

  ConfirmationToken getConfirmationToken(String token);

  void deleteConfirmationToken(ConfirmationToken confirmationToken);
}
