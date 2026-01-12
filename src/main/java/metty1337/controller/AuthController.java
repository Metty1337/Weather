package metty1337.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import metty1337.dto.SignInFormDto;
import metty1337.dto.SignUpFormDto;
import metty1337.entity.User;
import metty1337.service.AuthService;
import metty1337.service.SessionService;
import metty1337.service.UserService;
import metty1337.util.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Duration;

@PropertySource("classpath:session/session.properties")
@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final SessionService sessionService;
    private final UserService userService;
    private final Environment environment;
    private final String AUTH_TOKEN_COOKIE_NAME = "AUTH_TOKEN";


    @GetMapping("/signup")
    public String signUpPage(SignUpFormDto signUpFormDto) {
        return "sign-up";
    }

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute("signUpFormDto") SignUpFormDto signUpFormDto, BindingResult bindingResult) {
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
    public String signIn(@Valid @ModelAttribute("signInFormDto") SignInFormDto signInFormDto, BindingResult bindingResult, HttpServletResponse response) {
        log.debug("Attempting login, username={}", signInFormDto.getUsername());
        if (bindingResult.hasErrors()) {
            log.warn("Login form has errors, username={}", signInFormDto.getUsername());
            return "sign-in-with-errors";
        }
        String token = authService.authenticate(signInFormDto);
        long DURATION_IN_MIN_FOR_SESSION = Long.parseLong(environment.getRequiredProperty("durationInMin"));
        Duration duration = Duration.ofMinutes(DURATION_IN_MIN_FOR_SESSION);
        ResponseCookie cookie = CookieUtil.getCookie(token, duration);
        response.setHeader("Set-Cookie", cookie.toString());
        log.info("User logged in, username={}", signInFormDto.getUsername());
        return "index";
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtil.readCookie(request, AUTH_TOKEN_COOKIE_NAME);
        sessionService.logout(token);

        response.setHeader("Set-Cookie", CookieUtil.getEmptyCookie()
                                                   .toString());
        return "redirect:/auth/signin";
    }
}