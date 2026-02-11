package metty1337.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import metty1337.dto.WeatherDto;
import metty1337.entity.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

  private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
  private final OpenWeatherService openWeatherService;
  private final LocationService locationService;
  private final ExecutorService executorService;

  @Cacheable(cacheNames = "weather", key = "#userId")
  public List<WeatherDto> getWeather(Long userId) {
    log.info("Fetching weather for user {}", userId);
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
}
