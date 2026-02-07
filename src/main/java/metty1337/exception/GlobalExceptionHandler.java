package metty1337.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final String ERROR_PAGE = "error";
  private static final String EXCEPTION_ATTR = "exception";

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ModelAndView handleAny(Exception e) {
    ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
    modelAndView.addObject(EXCEPTION_ATTR, e);
    return modelAndView;
  }
}
