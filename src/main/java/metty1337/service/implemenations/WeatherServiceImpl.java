package metty1337.service.implemenations;

import java.util.List;
import lombok.RequiredArgsConstructor;
import metty1337.dto.WeatherDto;
import metty1337.entity.Location;
import metty1337.service.interfaces.LocationService;
import metty1337.service.interfaces.OpenWeatherService;
import metty1337.service.interfaces.WeatherService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

  private final OpenWeatherService openWeatherService;
  private final LocationService locationService;

  @Override
  public List<WeatherDto> getWeather(Long userId) {
    List<Location> locations = locationService.findAllByUser(userId);

    List<WeatherDto> weatherDtos = getWeatherDtos(locations);
    setOriginalNameAndCoordsForWeather(locations, weatherDtos);
    return weatherDtos;
  }

}
