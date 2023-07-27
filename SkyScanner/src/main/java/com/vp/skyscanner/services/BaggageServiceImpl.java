package com.vp.skyscanner.services;

import com.vp.skyscanner.models.Baggage;
import com.vp.skyscanner.models.Flight;
import com.vp.skyscanner.models.UserEntity;
import com.vp.skyscanner.repositories.BaggageRepository;
import com.vp.skyscanner.repositories.FlightRepository;
import com.vp.skyscanner.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaggageServiceImpl implements BaggageService {

  private final BaggageRepository baggageRepository;

  private final FlightRepository flightRepository;

  private final UserRepository userRepository;

  @Autowired
  public BaggageServiceImpl(BaggageRepository baggageRepository, FlightRepository flightRepository,
      UserRepository userRepository) {
    this.baggageRepository = baggageRepository;
    this.flightRepository = flightRepository;
    this.userRepository = userRepository;
  }

  @Override
  public String buyBaggage(long flightId, double weight, int amount, UserEntity user) {
    Optional<Flight> flightOptional = flightRepository.findById(flightId);
    if (!flightOptional.isPresent()) {
      return "Flight not found";
    }
    Flight flight = flightOptional.get();
    int baggageUnitWeight = 23;
    double totalBaggageUnits = Math.ceil(weight / baggageUnitWeight);
    if (totalBaggageUnits > amount) {
      return "Baggage is too heavy. You need to pay for more amount";
    }
    if (user.getMoney() < flight.getAirline().getBaggagePrice() * amount) {
      return "Not enough money";
    }
    List<Baggage> baggageList = new ArrayList<>();
    for (int i = 0; i < totalBaggageUnits; i++) {
      double currentBaggageWeight =
          (i == totalBaggageUnits - 1) ? weight - (totalBaggageUnits - 1) * baggageUnitWeight
              : baggageUnitWeight;
      Baggage baggage = new Baggage(currentBaggageWeight);
      baggage.setFlight(flight);
      baggage.setUser(user);
      user.getBaggage().add(baggage);
      baggageList.add(baggage);
    }

    user.setMoney(user.getMoney() - flight.getAirline().getBaggagePrice() * amount);

    flightRepository.save(flight);
    userRepository.save(user);
    baggageRepository.saveAll(baggageList);

    return "Baggage successfully bought";
  }
}
