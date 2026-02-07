package metty1337.service.interfaces;

import java.math.BigDecimal;
import java.util.List;
import metty1337.dto.LocationDto;
import metty1337.dto.WeatherDto;

public interface OpenWeatherService {

  WeatherDto getWeatherByCoords(BigDecimal latitude, BigDecimal longitude);

  List<LocationDto> getLocationsByName(String name);
}
