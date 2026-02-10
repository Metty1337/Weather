package metty1337.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import metty1337.constants.Constants;
import metty1337.dto.LocationDto;
import metty1337.dto.SearchFormDto;
import metty1337.dto.WeatherDto;
import metty1337.exception.LocationAlreadyAddedException;
import metty1337.service.LocationService;
import metty1337.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class IndexController {

  private static final String LOCATIONS_DTO_ATTR = "locationDtos";
  private static final String LOCATION_DTO_ATTR = "locationDto";
  private static final String WEATHER_ATTR = "weatherDtos";
  private static final String SEARCH_FORM_ATTR = "searchFormDto";
  private static final String INDEX_PAGE = "index";
  private static final String SEARCH_RESULTS_PAGE = "search-results";
  private static final Logger log = LoggerFactory.getLogger(IndexController.class);
  private final WeatherService weatherService;
  private final LocationService locationService;
  private static final String SESSION_LAST_SEARCH_ATTR = "lastSearchFormDto";
  private static final String SESSION_LAST_RESULTS_ATTR = "lastLocationDtos";


  @GetMapping({"/index", "/"})
  public String indexPage(HttpServletRequest request) {
    Optional<Long> userId = Optional.ofNullable(
        (Long) request.getAttribute(Constants.AUTH_USER_ID_ATTR));
    List<WeatherDto> weatherDtos = new ArrayList<>();
    if (userId.isPresent()) {
      weatherDtos = weatherService.getWeather(userId.get());
    }
    request.setAttribute(WEATHER_ATTR, weatherDtos);
    return INDEX_PAGE;
  }

  @PostMapping("/search")
  public String search(@Valid @ModelAttribute(SEARCH_FORM_ATTR) SearchFormDto searchFormDto,
      BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession session) {
    log.debug("Attempting to search for {}", searchFormDto);
    if (bindingResult.hasErrors()) {
      log.warn("Search form validation failed, searchFormDto={}", searchFormDto);
      redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + SEARCH_FORM_ATTR,
          bindingResult);
      redirectAttributes.addFlashAttribute(SEARCH_FORM_ATTR, searchFormDto);
      return "redirect:/search-results";
    }
    List<LocationDto> result = locationService.getLocations(searchFormDto);
    log.info("Found {} locations", result.size());
    session.setAttribute(SESSION_LAST_SEARCH_ATTR, searchFormDto);
    session.setAttribute(SESSION_LAST_RESULTS_ATTR, result);

    redirectAttributes.addFlashAttribute(SEARCH_FORM_ATTR, searchFormDto);
    redirectAttributes.addFlashAttribute(LOCATIONS_DTO_ATTR, result);
    return "redirect:/search-results";
  }

  @GetMapping("/search-results")
  public String searchResultPage(Model model, HttpSession session) {
    if (!model.containsAttribute(SEARCH_FORM_ATTR)) {
      SearchFormDto lastSearchFormDto = (SearchFormDto) session.getAttribute(
          SESSION_LAST_SEARCH_ATTR);
      model.addAttribute(SEARCH_FORM_ATTR,
          lastSearchFormDto != null ? lastSearchFormDto : new SearchFormDto());
    }

    if (!model.containsAttribute(LOCATIONS_DTO_ATTR)) {
      @SuppressWarnings("unchecked")
      List<LocationDto> lastLocationDtos = (List<LocationDto>) session.getAttribute(
          SESSION_LAST_RESULTS_ATTR);
      model.addAttribute(LOCATIONS_DTO_ATTR,
          lastLocationDtos != null ? lastLocationDtos : List.of());
    }
    return SEARCH_RESULTS_PAGE;
  }

  @PostMapping("/add")
  public String addLocation(@ModelAttribute(LOCATION_DTO_ATTR) LocationDto locationDto,
      HttpServletRequest request, RedirectAttributes redirectAttributes) {
    log.debug("Attempting to add location {}", locationDto);
    Long userId = (Long) request.getAttribute(Constants.AUTH_USER_ID_ATTR);

    try {
      locationService.addLocation(locationDto, userId);
    } catch (LocationAlreadyAddedException e) {
      redirectAttributes.addFlashAttribute("addLocationError", e.getMessage());
      log.warn("Location already added, locationDto={}", locationDto);
      return "redirect:/search-results";
    }
    log.info("Successfully added location {}", locationDto);
    return "redirect:/index";
  }

  @ModelAttribute(SEARCH_FORM_ATTR)
  public SearchFormDto searchFormDto() {
    return new SearchFormDto();
  }

  @PostMapping("/delete")
  public String deleteLocation(@RequestParam("latitude") String latitude,
      @RequestParam("longitude") String longitude,
      HttpServletRequest request) {
    log.debug("Attempting to delete location {}", latitude);
    Long userId = (Long) request.getAttribute(Constants.AUTH_USER_ID_ATTR);
    locationService.deleteLocation(latitude, longitude, userId);
    log.info("Successfully deleted location {}", latitude);
    return "redirect:/index";
  }
}
