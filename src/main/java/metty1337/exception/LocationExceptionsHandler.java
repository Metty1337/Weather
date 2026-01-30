package metty1337.exception;

import jakarta.servlet.http.HttpServletRequest;
import metty1337.dto.LocationDto;
import metty1337.dto.SearchFormDto;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class LocationExceptionsHandler {

  private static final String SEARCH_RESULTS_PAGE = "search-results";
  private static final String LOCATION_DTO_ATTR = "locationDto";
  private static final String SEARCH_FORM_DTO_ATTR = "searchFormDto";
  private static final String NAME_ATTR = "name";

  @ExceptionHandler(LocationAlreadyAddedException.class)
  public ModelAndView locationAlreadyExistException(LocationAlreadyAddedException ex, HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView(SEARCH_RESULTS_PAGE);

    BindingResult locationBindingResult = new BeanPropertyBindingResult(new LocationDto(),
        LOCATION_DTO_ATTR);
    locationBindingResult.reject(ex.getClass().getSimpleName(),
        ExceptionMessages.LOCATION_ALREADY_ADDED_EXCEPTION.getMessage());
    modelAndView.addObject(LOCATION_DTO_ATTR, new LocationDto());
    modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + LOCATION_DTO_ATTR, locationBindingResult);

    String name = request.getParameter(NAME_ATTR);
    SearchFormDto searchFormDto = new SearchFormDto(name);
    BindingResult searchBindingResult = new BeanPropertyBindingResult(searchFormDto,SEARCH_FORM_DTO_ATTR);
    modelAndView.addObject(SEARCH_FORM_DTO_ATTR, searchFormDto);
    modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + SEARCH_FORM_DTO_ATTR, searchBindingResult);

    return modelAndView;
  }
}
