package metty1337.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import metty1337.dto.WeatherDto;
import metty1337.service.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

  private static final String AUTH_USER_ID_ATTR = "AUTH_USER_ID";
  private static final String WEATHER_ATTR = "weatherDtos";
  private final WeatherService weatherService;

  @GetMapping("/index")
  public String indexPage(HttpServletRequest request) {
    Optional<Long> userId = Optional.ofNullable((Long) request.getAttribute(AUTH_USER_ID_ATTR));
    List<WeatherDto> weatherDtos = new ArrayList<>();
    if (userId.isPresent()) {
      weatherDtos = weatherService.getWeather(userId.get());
    }
    request.setAttribute(WEATHER_ATTR, weatherDtos);
    return "index";
  }
}
