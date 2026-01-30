package metty1337.exception;

import metty1337.dto.LocationDto;
import metty1337.dto.SignInFormDto;
import metty1337.dto.SignUpFormDto;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AuthExceptionsHandler {

  private static final String SIGN_UP_FORM_DTO_ATTR = "signUpFormDto";
  private static final String SIGN_UP_WITH_ERRORS_PAGE = "sign-up-with-errors";
  private static final String SIGN_IN_WITH_ERRORS_PAGE = "sign-in-with-errors";
  private static final String LOCATION_DTO_ATTR = "locationDto";
  private static final String SIGN_IN_FORM_DTO_ATTR = "signInFormDto";
  private static final String SEARCH_RESULTS_PAGE = "search-results";

  @ExceptionHandler(UserAlreadyExistException.class)
  public ModelAndView handleUserAlreadyExistsException(UserAlreadyExistException ex) {
    ModelAndView modelAndView = new ModelAndView(SIGN_UP_WITH_ERRORS_PAGE);
    BindingResult bindingResult =
        new BeanPropertyBindingResult(new SignUpFormDto(), SIGN_UP_FORM_DTO_ATTR);
    bindingResult.reject(
        ex.getClass().getSimpleName(), ExceptionMessages.USER_ALREADY_EXIST_EXCEPTION.getMessage());

    modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + SIGN_UP_FORM_DTO_ATTR, bindingResult);
    modelAndView.addObject(SIGN_UP_FORM_DTO_ATTR, new SignUpFormDto());

    return modelAndView;
  }

  @ExceptionHandler(AuthenticationFailedException.class)
  public ModelAndView handleAuthenticationFailedException(AuthenticationFailedException ex) {
    ModelAndView modelAndView = new ModelAndView(SIGN_IN_WITH_ERRORS_PAGE);
    BindingResult bindingResult =
        new BeanPropertyBindingResult(new SignInFormDto(), SIGN_IN_FORM_DTO_ATTR);
    bindingResult.reject(
        ex.getClass().getSimpleName(),
        ExceptionMessages.AUTHENTICATION_FAILED_EXCEPTION.getMessage());

    modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + SIGN_IN_FORM_DTO_ATTR, bindingResult);
    modelAndView.addObject(SIGN_IN_FORM_DTO_ATTR, new SignInFormDto());

    return modelAndView;
  }

  @ExceptionHandler(UserDoesNotAuthorizedException.class)
  public ModelAndView handleUserDoesNotAuthorizedException(UserDoesNotAuthorizedException ex) {
    ModelAndView modelAndView = new ModelAndView(SEARCH_RESULTS_PAGE);
    BindingResult bindingResult = new BeanPropertyBindingResult(new LocationDto(),
        LOCATION_DTO_ATTR);
    bindingResult.reject(
        ex.getClass().getSimpleName(),
        ExceptionMessages.USER_DOES_NOT_AUTHORIZED_EXCEPTION.getMessage()
    );
    modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + LOCATION_DTO_ATTR, bindingResult);
    modelAndView.addObject(LOCATION_DTO_ATTR, new LocationDto());
    return modelAndView;
  }
}
