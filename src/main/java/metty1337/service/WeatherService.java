package metty1337.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import metty1337.dto.WeatherDto;
import metty1337.entity.Location;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

  private final OpenWeatherService openWeatherService;
  private final LocationService locationService;
  private final ExecutorService executorService;

  public List<WeatherDto> getWeather(Long userId) {
    List<Location> locations = locationService.findAllByUser(userId);

    List<CompletableFuture<WeatherDto>> futures = locations.stream()
        .map(location -> CompletableFuture.supplyAsync(
            () -> {
              WeatherDto weatherDto = openWeatherService.getWeatherByCoords(
                  location.getLatitude(),
                  location.getLongitude());
              return getOriginalNameAndCoordsForWeather(location, weatherDto);
            },
            executorService
        ))
        .toList();
    return futures.stream()
        .map(CompletableFuture::join)
        .toList();
  }

  private static WeatherDto getOriginalNameAndCoordsForWeather(Location location,
      WeatherDto weatherDto) {
    return new WeatherDto(
        location.getName(),
        weatherDto.getCountry(),
        weatherDto.getTemp(),
        weatherDto.getFeelsLike(),
        weatherDto.getWeather(),
        weatherDto.getHumidity(),
        weatherDto.getIcon(),
        String.valueOf(location.getLatitude()),
        String.valueOf(location.getLongitude()));
  }

//  private static List<WeatherDto> getOriginalNameAndCoordsForWeather(List<Location> locations,
//      List<WeatherDto> weatherDtos) {
//    List<WeatherDto> newWeatherDtos = List.copyOf(weatherDtos);
//
//    for (int i = 0; i < locations.size(); i++) {
//      WeatherDto weatherDto = newWeatherDtos.get(i);
//      Location location = locations.get(i);
//      weatherDto.setName(location.getName());
//      weatherDto.setLatitude(String.valueOf(location.getLatitude()));
//      weatherDto.setLongitude(String.valueOf(location.getLongitude()));
//    }
//    return newWeatherDtos;
//  }
}
