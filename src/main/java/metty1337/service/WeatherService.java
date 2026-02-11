package metty1337.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import metty1337.dto.WeatherDto;
import metty1337.entity.Location;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

  private final OpenWeatherService openWeatherService;
  private final LocationService locationService;

  public List<WeatherDto> getWeather(Long userId) {
    List<Location> locations = locationService.findAllByUser(userId);

    List<WeatherDto> weatherDtos = getWeatherDtos(locations);
    return setOriginalNameAndCoordsForWeather(locations, weatherDtos);
  }

  private @NonNull List<WeatherDto> getWeatherDtos(List<Location> locations) {
    return locations.stream().map(
        location -> openWeatherService.getWeatherByCoords(location.getLatitude(),
            location.getLongitude())).toList();
  }

  private static List<WeatherDto> setOriginalNameAndCoordsForWeather(List<Location> locations,
      List<WeatherDto> weatherDtos) {
    List<WeatherDto> newWeatherDtos = List.copyOf(weatherDtos);

    for (int i = 0; i < locations.size(); i++) {
      WeatherDto weatherDto = newWeatherDtos.get(i);
      Location location = locations.get(i);
      weatherDto.setName(location.getName());
      weatherDto.setLatitude(String.valueOf(location.getLatitude()));
      weatherDto.setLongitude(String.valueOf(location.getLongitude()));
    }
    return newWeatherDtos;
  }
}
