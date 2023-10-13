package com.vp.skyscanner.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;

  @Autowired
  public EmailServiceImpl(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  @Async
  public void sendEmail(String receiverEmail, String emailTemplate, String subject) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

      helper.setText(emailTemplate, true);
      helper.setTo(receiverEmail);
      helper.setSubject(subject);
      helper.setFrom("skyscanner.clone@mail.com");
      mailSender.send(mimeMessage);

    } catch (MessagingException e) {
      System.err.println("Failed to send email");
      throw new IllegalStateException("Failed to send email");
    }
  }

  @Override
  public void sendEmail(String receiverEmail, String emailTemplate) {

  }
}

