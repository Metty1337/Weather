package metty1337.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import metty1337.entity.User;
import metty1337.interceptor.AuthTokenInterceptor;
import metty1337.service.UserService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@RequiredArgsConstructor
@ControllerAdvice
public class CurrentUserAdvice {
    private final UserService userService;

    @ModelAttribute("currentUser")
    public Optional<User> getCurrentUser(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute(AuthTokenInterceptor.AUTH_USER_ID_ATTR);
        if (userId == null) {
            return Optional.empty();
        }
        return userService.findById(userId);
    }
}
