package metty1337.service;

import java.time.Duration;
import java.time.Instant;
import metty1337.dto.SignInFormDto;
import metty1337.entity.Session;
import metty1337.entity.User;
import metty1337.exception.AuthenticationFailedException;
import metty1337.exception.ExceptionMessages;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

  private final String sessionExpiration;
  private final PasswordEncoder passwordEncoder;
  private final SessionService sessionService;
  private final UserService userService;

  public AuthService(@Value("${durationInMin}") String sessionExpiration,
      PasswordEncoder passwordEncoder, SessionService sessionService, UserService userService) {
    this.sessionExpiration = sessionExpiration;
    this.passwordEncoder = passwordEncoder;
    this.sessionService = sessionService;
    this.userService = userService;
  }

  @Transactional
  public String authenticate(SignInFormDto signInFormDto) {
    User user = userService.findByLogin(signInFormDto.getUsername())
        .orElseThrow(() -> new AuthenticationFailedException(
            ExceptionMessages.AUTHENTICATION_FAILED_EXCEPTION.getMessage()));
    if (!passwordEncoder.matches(signInFormDto.getPassword(), user.getPassword())) {
      throw new AuthenticationFailedException(
          ExceptionMessages.AUTHENTICATION_FAILED_EXCEPTION.getMessage());
    }

    Instant now = Instant.now();
    Duration sessionExpiration = Duration.ofMinutes(Long.parseLong(this.sessionExpiration));
    Session session = new Session(user, now.plus(sessionExpiration));
    sessionService.createSession(session);
    return session.getId()
        .toString();
  }
}
