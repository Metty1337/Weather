package metty1337.service.interfaces;

import java.util.List;
import metty1337.dto.WeatherDto;
import metty1337.entity.Location;
import org.jspecify.annotations.NonNull;

public interface WeatherService {

  static void setOriginalNameAndCoordsForWeather(List<Location> locations,
      List<WeatherDto> weatherDtos) {
    for (int i = 0; i < locations.size(); i++) {
      WeatherDto weatherDto = weatherDtos.get(i);
      Location location = locations.get(i);
      weatherDto.setName(location.getName());
      weatherDto.setLatitude(String.valueOf(location.getLatitude()));
      weatherDto.setLongitude(String.valueOf(location.getLongitude()));
    }
  }

  List<WeatherDto> getWeather(Long userId);

  @NonNull
  default List<WeatherDto> getWeatherDtos(List<Location> locations) {
    return locations.stream()
        .map(location ->
            openWeatherService.getWeatherByCoords(
                location.getLatitude(),
                location.getLongitude()
            )
        )
        .toList();
  }
}
