package metty1337.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.time.Duration;
import metty1337.constants.Constants;
import metty1337.dto.SignInFormDto;
import metty1337.dto.SignUpFormDto;
import metty1337.entity.User;
import metty1337.service.AuthService;
import metty1337.service.SessionService;
import metty1337.service.UserService;
import metty1337.util.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

  private static final Logger log = LoggerFactory.getLogger(AuthController.class);
  private static final String SIGN_UP_FORM_DTO_ATTR = "signUpFormDto";
  private static final String SIGN_IN_FORM_DTO_ATTR = "signInFormDto";

  @Value("${durationInMin}")
  private final long cookieDurationMinutes;
  private final AuthService authService;
  private final SessionService sessionService;
  private final UserService userService;

  public AuthController(
      @Value("${durationInMin}") long cookieDurationMinutes,
      AuthService authService,
      SessionService sessionService,
      UserService userService) {
    this.cookieDurationMinutes = cookieDurationMinutes;
    this.authService = authService;
    this.sessionService = sessionService;
    this.userService = userService;
  }

  @GetMapping("/signup")
  public String signUpPage(Model model) {
    if (!model.containsAttribute(SIGN_UP_FORM_DTO_ATTR)) {
      model.addAttribute(SIGN_UP_FORM_DTO_ATTR, new SignUpFormDto());
    }
    return "sign-up";
  }

  @PostMapping("/signup")
  public String signUp(
      @Valid @ModelAttribute(SIGN_UP_FORM_DTO_ATTR) SignUpFormDto signUpFormDto,
      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    log.debug("Attempting registration, username={}", signUpFormDto.getUsername());

    if (bindingResult.hasErrors()) {
      log.warn("Registration form has errors, username={}", signUpFormDto.getUsername());
      redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + SIGN_UP_FORM_DTO_ATTR,
          bindingResult);
      redirectAttributes.addFlashAttribute(SIGN_UP_FORM_DTO_ATTR, signUpFormDto);

      return "redirect:/auth/signup-with-errors";
    }
    User user = userService.createUser(signUpFormDto);
    log.info("User registered, userId={}", user.getId());

    return "redirect:/auth/signin";
  }

  @GetMapping("/signup-with-errors")
  public String signUpWithErrorsPage(Model model) {
    if (!model.containsAttribute(SIGN_UP_FORM_DTO_ATTR)) {
      model.addAttribute(SIGN_UP_FORM_DTO_ATTR, new SignUpFormDto());
    }
    return "sign-up-with-errors";
  }

  @GetMapping("/signin")
  public String signInPage(Model model) {
    if (!model.containsAttribute(SIGN_IN_FORM_DTO_ATTR)) {
      model.addAttribute(SIGN_IN_FORM_DTO_ATTR, new SignInFormDto());
    }
    return "sign-in";
  }

  @PostMapping("/signin")
  public String signIn(
      @Valid @ModelAttribute(SIGN_IN_FORM_DTO_ATTR) SignInFormDto signInFormDto,
      BindingResult bindingResult,
      HttpServletResponse response, RedirectAttributes redirectAttributes) {
    log.debug("Attempting login, username={}", signInFormDto.getUsername());

    if (bindingResult.hasErrors()) {
      log.warn("Login form has errors, username={}", signInFormDto.getUsername());
      redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + SIGN_IN_FORM_DTO_ATTR,
          bindingResult);
      redirectAttributes.addFlashAttribute(SIGN_IN_FORM_DTO_ATTR, signInFormDto);
      return "redirect:/auth/signin-with-errors";
    }
    String token = authService.authenticate(signInFormDto);

    Duration duration = Duration.ofMinutes(cookieDurationMinutes);
    ResponseCookie cookie = CookieUtil.getCookie(token, Constants.AUTH_TOKEN_COOKIE_NAME, duration);
    response.setHeader("Set-Cookie", cookie.toString());
    log.info("User logged in, username={}", signInFormDto.getUsername());

    return "redirect:/index";
  }

  @GetMapping("/signin-with-errors")
  public String signInWithErrors(Model model) {
    if (!model.containsAttribute(SIGN_IN_FORM_DTO_ATTR)) {
      model.addAttribute(SIGN_IN_FORM_DTO_ATTR, new SignInFormDto());
    }
    return "sign-in-with-errors";
  }

  @PostMapping("/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    String token = CookieUtil.readCookie(request, Constants.AUTH_TOKEN_COOKIE_NAME);
    sessionService.logout(token);

    response.setHeader("Set-Cookie",
        CookieUtil.getEmptyCookie(Constants.AUTH_TOKEN_COOKIE_NAME).toString());
    return "redirect:/auth/signin";
  }
}