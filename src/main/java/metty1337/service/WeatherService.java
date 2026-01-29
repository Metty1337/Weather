package metty1337.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import metty1337.dto.WeatherDto;
import metty1337.entity.Location;
import metty1337.entity.User;
import metty1337.exception.ExceptionMessages;
import metty1337.exception.UserDoesNotExistException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

  private final OpenWeatherService openWeatherService;
  private final UserService userService;
  private final LocationService locationService;

  public List<WeatherDto> getWeather(Long userId) {
    User user = userService.findById(userId).orElseThrow(() -> (new UserDoesNotExistException(
        ExceptionMessages.USER_DOES_NOT_EXIST_EXCEPTION.getMessage())));
    List<Location> locations = locationService.findAllByUser(user);

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
