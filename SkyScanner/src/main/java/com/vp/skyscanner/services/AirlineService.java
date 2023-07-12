package com.vp.skyscanner.services;

import com.vp.skyscanner.dtos.AirlineDto;
import java.util.List;

public interface AirlineService {

  List<AirlineDto> showAllAirlines();
}
