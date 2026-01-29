package metty1337.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import metty1337.dto.LocationDto;
import metty1337.dto.SearchFormDto;
import metty1337.dto.WeatherDto;
import metty1337.service.LocationService;
import metty1337.service.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

  private static final String AUTH_USER_ID_ATTR = "AUTH_USER_ID";
  private static final String LOCATIONS_ATTR = "locationDtos";
  private static final String WEATHER_ATTR = "weatherDtos";
  private static final String SEARCH_FORM_ATTR = "searchFormDto";
  private final WeatherService weatherService;
  private final LocationService locationService;

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

  @PostMapping("/search")
  public String search(@Valid @ModelAttribute(SEARCH_FORM_ATTR) SearchFormDto searchFormDto,
      HttpServletRequest request) {
    List<LocationDto> locationDtos = locationService.getLocations(searchFormDto);
    request.setAttribute(LOCATIONS_ATTR, locationDtos);
    return "search-results";
  }

  @ModelAttribute(SEARCH_FORM_ATTR)
  public SearchFormDto searchFormDto() {
    return new SearchFormDto();
  }
}
