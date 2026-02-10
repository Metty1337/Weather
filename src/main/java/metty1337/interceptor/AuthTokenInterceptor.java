package metty1337.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import metty1337.constants.Constants;
import metty1337.service.SessionService;
import metty1337.util.CookieUtil;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthTokenInterceptor implements HandlerInterceptor {

  private final SessionService sessionService;

  @Override
  public boolean preHandle(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull Object handler) {
    String token = CookieUtil.readCookie(request, Constants.AUTH_TOKEN_COOKIE_NAME);
    if (token.isBlank()) {
      return true;
    }

    sessionService
        .findByToken(token)
        .filter(s -> s.getExpiresAt().isAfter(Instant.now()))
        .ifPresent(s -> request.setAttribute(Constants.AUTH_USER_ID_ATTR, s.getUser().getId()));
    return true;
  }
}
