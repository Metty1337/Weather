package metty1337.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.time.Duration;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

  private static final Logger log = LoggerFactory.getLogger(AuthController.class);
  private static final String AUTH_TOKEN_COOKIE_NAME = "AUTH_TOKEN";
  private final long TIME_TO_COOKIE_LIVE;
  private final AuthService authService;
  private final SessionService sessionService;
  private final UserService userService;

  public AuthController(
      @Value("${durationInMin}") long TIME_TO_COOKIE_LIVE,
      AuthService authService,
      SessionService sessionService,
      UserService userService) {
    this.TIME_TO_COOKIE_LIVE = TIME_TO_COOKIE_LIVE;
    this.authService = authService;
    this.sessionService = sessionService;
    this.userService = userService;
  }

  @GetMapping("/signup")
  public String signUpPage(SignUpFormDto signUpFormDto) {
    return "sign-up";
  }

  @PostMapping("/signup")
  public String signUp(
      @Valid @ModelAttribute("signUpFormDto") SignUpFormDto signUpFormDto,
      BindingResult bindingResult) {
    log.debug("Attempting registration, username={}", signUpFormDto.getUsername());

    if (bindingResult.hasErrors()) {
      log.warn("Registration form has errors, username={}", signUpFormDto.getUsername());
      return "sign-up-with-errors";
    }
    User user = userService.createUser(signUpFormDto);
    log.info("User registered, userId={}", user.getId());

    return "redirect:/auth/signin";
  }

  @GetMapping("/signin")
  public String signInPage(SignInFormDto signInFormDto) {
    return "sign-in";
  }

  @PostMapping("/signin")
  public String signIn(
      @Valid @ModelAttribute("signInFormDto") SignInFormDto signInFormDto,
      BindingResult bindingResult,
      HttpServletResponse response) {
    log.debug("Attempting login, username={}", signInFormDto.getUsername());

    if (bindingResult.hasErrors()) {
      log.warn("Login form has errors, username={}", signInFormDto.getUsername());
      return "sign-in-with-errors";
    }
    String token = authService.authenticate(signInFormDto);

    Duration duration = Duration.ofMinutes(TIME_TO_COOKIE_LIVE);
    ResponseCookie cookie = CookieUtil.getCookie(token, AUTH_TOKEN_COOKIE_NAME, duration);
    response.setHeader("Set-Cookie", cookie.toString());
    log.info("User logged in, username={}", signInFormDto.getUsername());

    return "redirect:/index";
  }

  @PostMapping("/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    String token = CookieUtil.readCookie(request, AUTH_TOKEN_COOKIE_NAME);
    sessionService.logout(token);

    response.setHeader("Set-Cookie", CookieUtil.getEmptyCookie(AUTH_TOKEN_COOKIE_NAME).toString());
    return "redirect:/auth/signin";
  }
}