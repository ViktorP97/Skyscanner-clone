package com.vp.skyscanner.services;

import com.vp.skyscanner.models.Flight;
import com.vp.skyscanner.models.PriceAlertEntity;
import com.vp.skyscanner.repositories.FlightRepository;
import com.vp.skyscanner.repositories.PriceAlertEntityRepository;
import com.vp.skyscanner.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class PriceAlertEntityServiceImpl implements PriceAlertEntityService {

  private final UserRepository userRepository;
  private final FlightRepository flightRepository;
  private final EmailService emailService;
  private final PriceAlertEntityRepository priceAlertEntityRepository;

  @Autowired
  public PriceAlertEntityServiceImpl(UserRepository userRepository,
      FlightRepository flightRepository, EmailService emailService,
      PriceAlertEntityRepository priceAlertEntityRepository) {
    this.userRepository = userRepository;
    this.flightRepository = flightRepository;
    this.emailService = emailService;
    this.priceAlertEntityRepository = priceAlertEntityRepository;
  }

  @Override
  public void alertFlight() {
    List<Pair<PriceAlertEntity, Flight>> matchingPairs = findMatchingAlert();
    if (!matchingPairs.isEmpty()) {
      for (Pair<PriceAlertEntity, Flight> matchingPair : matchingPairs) {
        PriceAlertEntity priceAlert = matchingPair.getFirst();
        Flight flight = matchingPair.getSecond();


        String emailSubject = "Price alert for Flight " + flight.getFlightNumber();
        emailService.sendEmail(
            priceAlert.getUser().getEmail(),
            buildEmail(priceAlert.getUser().getUsername()),
            emailSubject
        );
      }
    }
  }

  private List<Pair<PriceAlertEntity, Flight>> findMatchingAlert() {
    List<Pair<PriceAlertEntity, Flight>> matched = new ArrayList<>();
    List<PriceAlertEntity> priceAlertEntities = priceAlertEntityRepository.findAll();
    List<Flight> flights = flightRepository.findAll();
    for (PriceAlertEntity alertEntity : priceAlertEntities) {
      for (Flight flight : flights) {
        if (alertEntity.getDeparture().equals(flight.getDeparture())
            && alertEntity.getDestination().equals(flight.getArrive())
            && alertEntity.getPrice() >= flight.getPrice()) {
          matched.add(Pair.of(alertEntity, flight));
        }
      }
    }
    return matched;
  }

  private String buildEmail(String username) {
    return
        "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n\n"
            + "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n\n"
            + "  <table role=\"presentation\" width=\"100%\""
            + " style=\"border-collapse:collapse;min-width:100%;width:100%!important\""
            + " cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
            + "    <tbody><tr>\n"
            + "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n\n"
            + "        <table role=\"presentation\" width=\"100%\""
            + " style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\""
            + " cellspacing=\"0\" border=\"0\" align=\"center\">\n"
            + "          <tbody><tr>\n"
            + "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n"
            + "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\""
            + " border=\"0\" style=\"border-collapse:collapse\">\n"
            + "                  <tbody><tr>\n"
            + "<td style=\"padding-left:10px\">\n\n"
            + "                    </td>\n"
            + "<td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n"
            + "                      <span"
            + " style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm"
            + " your email</span>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody></table>\n"
            + "</a>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody></table>\n\n"
            + "</td>\n"
            + "</tr>\n"
            + "  </tbody></table>\n"
            + "  <table role=\"presentation\" class=\"m_-6186904992287805515content\""
            + " align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\""
            + " style=\"border-collapse:collapse;max-width:580px;width:100%!important\""
            + " width=\"100%\">\n"
            + "    <tbody><tr>\n"
            + "<td width=\"10\" height=\"10\" valign=\"middle\"></td>\n"
            + "      <td>\n\n"
            + "<table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\""
            + " border=\"0\" style=\"border-collapse:collapse\">\n"
            + "                  <tbody><tr>\n"
            + "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n"
            + "                  </tr>\n"
            + "</tbody></table>\n\n"
            + "</td>\n"
            + "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n"
            + "    </tr>\n"
            + "  </tbody></table>\n\n\n\n"
            + "  <table role=\"presentation\" class=\"m_-6186904992287805515content\""
            + " align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\""
            + " style=\"border-collapse:collapse;max-width:580px;width:100%!important\""
            + " width=\"100%\">\n"
            + "    <tbody><tr>\n"
            + "      <td height=\"30\"><br></td>\n"
            + "    </tr>\n"
            + "    <tr>\n"
            + "      <td width=\"10\" valign=\"middle\"><br></td>\n"
            + "      <td"
            + " style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n\n"
            + "<p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi "
            + username
            + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">"
            + " We found a ticket for you. You can visit our page, to buy a ticket."
            + "</p></blockquote>\n"
            + "\n</td>\n"
            + "      <td width=\"10\" valign=\"middle\"><br></td>\n"
            + "    </tr>\n<tr>\n<td height=\"30\"><br></td>\n</tr>\n"
            + "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n\n"
            + "</div></div>";
  }
}
