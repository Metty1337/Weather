package metty1337.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import metty1337.service.interfaces.SessionService;
import metty1337.util.CookieUtil;
import org.jspecify.annotations.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthTokenInterceptor implements HandlerInterceptor {

  private static final String AUTH_USER_ID_ATTR = "AUTH_USER_ID";
  private static final String AUTH_TOKEN_ATTR = "AUTH_TOKEN";
  private final SessionService sessionService;

  @Override
  public boolean preHandle(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull Object handler) {
    String token = CookieUtil.readCookie(request, AUTH_TOKEN_ATTR);
    if (token.isBlank()) {
      return true;
    }

    sessionService
        .findByToken(token)
        .filter(s -> s.getExpiresAt().isAfter(Instant.now()))
        .ifPresent(s -> request.setAttribute(AUTH_USER_ID_ATTR, s.getUser().getId()));
    return true;
  }
}
