package metty1337.advice;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import metty1337.dto.UserDto;
import metty1337.entity.User;
import metty1337.mapper.UserMapper;
import metty1337.service.interfaces.UserService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequiredArgsConstructor
@ControllerAdvice
public class CurrentUserAdvice {

  private static final String AUTH_USER_ID_ATTR = "AUTH_USER_ID";
  private final UserService userService;
  private final UserMapper userMapper;

  @ModelAttribute("currentUser")
  public Optional<UserDto> getCurrentUser(HttpServletRequest request) {
    Long userId = (Long) request.getAttribute(AUTH_USER_ID_ATTR);
    if (userId == null) {
      return Optional.empty();
    }
    Optional<User> user = userService.findById(userId);
    return user.map(userMapper::entityToDto);
  }
}
