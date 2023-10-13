package com.vp.skyscanner.services;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {

  @Async
  void sendEmail(String receiverEmail, String emailTemplate, String subject);

  void sendEmail(String receiverEmail, String emailTemplate);

}
