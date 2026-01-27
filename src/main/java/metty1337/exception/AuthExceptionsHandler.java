package metty1337.exception;

import metty1337.dto.SignInFormDto;
import metty1337.dto.SignUpFormDto;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AuthExceptionsHandler {

  private static final String SIGN_UP_FORM_DTO = "signUpFormDto";
  private static final String SIGN_UP_WITH_ERRORS = "sign-up-with-errors";
  private static final String SIGN_IN_WITH_ERRORS = "sign-in-with-errors";
  private static final String SIGN_IN_FORM_DTO = "signInFormDto";

  @ExceptionHandler(UserAlreadyExistException.class)
  public ModelAndView handleUserAlreadyExistsException(UserAlreadyExistException ex) {
    ModelAndView modelAndView = new ModelAndView(SIGN_UP_WITH_ERRORS);
    BindingResult bindingResult =
        new BeanPropertyBindingResult(new SignUpFormDto(), SIGN_UP_FORM_DTO);
    bindingResult.reject(
        ex.getClass().getSimpleName(), ExceptionMessages.USER_ALREADY_EXIST_EXCEPTION.getMessage());

    modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + SIGN_UP_FORM_DTO, bindingResult);
    modelAndView.addObject(SIGN_UP_FORM_DTO, new SignUpFormDto());

    return modelAndView;
  }

  @ExceptionHandler(AuthenticationFailedException.class)
  public ModelAndView handleAuthenticationFailedException(AuthenticationFailedException ex) {
    ModelAndView modelAndView = new ModelAndView(SIGN_IN_WITH_ERRORS);
    BindingResult bindingResult =
        new BeanPropertyBindingResult(new SignInFormDto(), SIGN_IN_FORM_DTO);
    bindingResult.reject(
        ex.getClass().getSimpleName(),
        ExceptionMessages.AUTHENTICATION_FAILED_EXCEPTION.getMessage());

    modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + SIGN_IN_FORM_DTO, bindingResult);
    modelAndView.addObject(SIGN_IN_FORM_DTO, new SignInFormDto());

    return modelAndView;
  }
}
