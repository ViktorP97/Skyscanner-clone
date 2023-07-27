package com.vp.skyscanner;

import com.vp.skyscanner.models.Airline;
import com.vp.skyscanner.models.Flight;
import com.vp.skyscanner.repositories.AirlineRepository;
import com.vp.skyscanner.repositories.FlightRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SkyScannerApplication implements CommandLineRunner {

  private final AirlineRepository airlineRepository;
  private final FlightRepository flightRepository;

  @Autowired
  public SkyScannerApplication(AirlineRepository airlineRepository,
      FlightRepository flightRepository) {
    this.airlineRepository = airlineRepository;
    this.flightRepository = flightRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(SkyScannerApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Airline airline1 = new Airline();
    airline1.setName("Qatar");
    airline1.setBaggagePrice(50);
    Airline savedAirline1 = airlineRepository.save(airline1);

    Flight flight1 = new Flight();
    flight1.setFlightNumber(123);
    flight1.setDeparture("Kosice");
    flight1.setArrive("New York");
    flight1.setTimeOfFlight(LocalDateTime.of(2023, 5, 8, 14, 15));
    flight1.setTimeOfArrive(LocalDateTime.of(2023, 5, 8, 17, 30));
    flight1.setAvailableSeats(5);
    flight1.setPrice(150);
    flight1.setAirline(savedAirline1);
    flightRepository.save(flight1);

    Flight flight4 = new Flight();
    flight4.setFlightNumber(788);
    flight4.setDeparture("Kosice");
    flight4.setArrive("New York");
    flight4.setTimeOfFlight(LocalDateTime.of(2023, 7, 14, 8, 15));
    flight4.setTimeOfArrive(LocalDateTime.of(2023, 7, 15, 14, 15));
    flight4.setAvailableSeats(2);
    flight4.setPrice(500);
    flight4.setAirline(savedAirline1);
    flightRepository.save(flight4);

    Airline airline2 = new Airline();
    airline2.setName("Emirates");
    airline2.setBaggagePrice(40);
    Airline savedAirline2 = airlineRepository.save(airline2);

    Flight flight2 = new Flight();
    flight2.setFlightNumber(1457);
    flight2.setDeparture("Prague");
    flight2.setArrive("Denver");
    flight2.setTimeOfFlight(LocalDateTime.of(2023, 4, 17, 17, 30));
    flight2.setTimeOfArrive(LocalDateTime.of(2023, 4, 18, 10, 30));
    flight2.setAvailableSeats(44);
    flight2.setPrice(350);
    flight2.setAirline(savedAirline2);
    flightRepository.save(flight2);

    Flight flight6 = new Flight();
    flight6.setFlightNumber(1785);
    flight6.setDeparture("Prague");
    flight6.setArrive("Denver");
    flight6.setTimeOfFlight(LocalDateTime.of(2023, 4, 17, 17, 30));
    flight6.setTimeOfArrive(LocalDateTime.of(2023, 4, 19, 10, 30));
    flight6.setAvailableSeats(44);
    flight6.setPrice(150);
    flight6.setAirline(savedAirline2);
    flightRepository.save(flight6);

    Flight flight7 = new Flight();
    flight7.setFlightNumber(4751);
    flight7.setDeparture("Prague");
    flight7.setArrive("Denver");
    flight7.setTimeOfFlight(LocalDateTime.of(2023, 4, 17, 17, 30));
    flight7.setTimeOfArrive(LocalDateTime.of(2023, 4, 20, 10, 30));
    flight7.setAvailableSeats(44);
    flight7.setPrice(400);
    flight7.setAirline(savedAirline2);
    flightRepository.save(flight7);

    Flight flight3 = new Flight();
    flight3.setFlightNumber(475);
    flight3.setDeparture("Toronto");
    flight3.setArrive("London");
    flight3.setTimeOfFlight(LocalDateTime.of(2023, 8, 29, 4, 30));
    flight3.setTimeOfArrive(LocalDateTime.of(2023, 8, 30, 17, 30));
    flight3.setAvailableSeats(0);
    flight3.setPrice(800);
    flight3.setAirline(savedAirline2);
    flightRepository.save(flight3);
  }
}
