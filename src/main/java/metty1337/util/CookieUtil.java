package metty1337.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseCookie;

import java.time.Duration;
import java.util.Optional;

@UtilityClass
public class CookieUtil {
    public static String readCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "";
        }
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "";
    }

    public static @NonNull ResponseCookie getCookie(String token, String cookieName, Duration duration) {
        return ResponseCookie.from(cookieName, token)
                             .httpOnly(true)
                             .secure(false)
                             .path("/")
                             .sameSite("Lax")
                             .maxAge(duration)
                             .build();
    }

    public static @NonNull ResponseCookie getEmptyCookie(String cookieName) {
        return getCookie("", cookieName, Duration.ZERO);
    }
}
