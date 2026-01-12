package metty1337.service;

import lombok.RequiredArgsConstructor;
import metty1337.dto.SignInFormDto;
import metty1337.dto.SignUpFormDto;
import metty1337.entity.Session;
import metty1337.entity.User;
import metty1337.exception.ExceptionMessages;
import metty1337.exception.AuthenticationFailedException;
import metty1337.repository.SessionRepository;
import metty1337.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Duration SESSION_EXPIRATION = Duration.ofMinutes(5);
    private final PasswordEncoder passwordEncoder;
    private final SessionService sessionService;
    private final UserService userService;

    @Transactional
    public String authenticate(SignInFormDto signInFormDto) {
        User user = userService.findByLogin(signInFormDto.getUsername())
                               .orElseThrow(() -> new AuthenticationFailedException(ExceptionMessages.AUTHENTICATION_FAILED_EXCEPTION.getMessage()));
        if (!passwordEncoder.matches(signInFormDto.getPassword(), user.getPassword())) {
            throw new AuthenticationFailedException(ExceptionMessages.AUTHENTICATION_FAILED_EXCEPTION.getMessage());
        }

        Instant now = Instant.now();

        Session session = new Session(user, now.plus(SESSION_EXPIRATION));
        sessionService.createSession(session);
        return session.getId()
                      .toString();
    }
}
