import metty1337.config.AppConfig;
import metty1337.config.TestConfig;
import metty1337.config.WebConfig;
import metty1337.dto.SignUpFormDto;
import metty1337.entity.Session;
import metty1337.entity.User;
import metty1337.exception.UserAlreadyExistException;
import metty1337.job.SessionCleanupJob;
import metty1337.repository.SessionRepository;
import metty1337.repository.UserRepository;
import metty1337.service.SessionService;
import metty1337.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;

@SpringJUnitConfig
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class, TestConfig.class, WebConfig.class})
@ActiveProfiles("test")
public class UserServiceIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;


    @BeforeEach
    public void setUp() {
        sessionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void shouldCreateAndFindUser() {
        SignUpFormDto signUpFormDto = new SignUpFormDto("Alex", "12345678", "12345678");
        String username = signUpFormDto.getUsername();

        UserService userService = webApplicationContext.getBean(UserService.class);
        userService.createUser(signUpFormDto);

        Assertions.assertTrue(userService.findByLogin(username)
                                         .isPresent());
    }

    @Test
    public void shouldThrowExceptionWhenUserAlreadyExists() {
        SignUpFormDto signUpFormDto = new SignUpFormDto("Alex", "12345678", "12345678");
        UserService userService = webApplicationContext.getBean(UserService.class);
        userService.createUser(signUpFormDto);

        Assertions.assertThrows(UserAlreadyExistException.class, () -> userService.createUser(signUpFormDto));
    }

    @Test
    public void shouldDeleteSessionAfterExpired() {
        SignUpFormDto signUpFormDto = new SignUpFormDto("Alex", "12345678", "12345678");
        UserService userService = webApplicationContext.getBean(UserService.class);
        User user = userService.createUser(signUpFormDto);

        SessionService sessionService = webApplicationContext.getBean(SessionService.class);

        Session expired = new Session(user, Instant.now()
                                                   .minusSeconds(350));
        Session active = new Session(user, Instant.now()
                                                  .plusSeconds(350));
        sessionService.createSession(expired);
        sessionService.createSession(active);

        SessionCleanupJob sessionCleanupJob = webApplicationContext.getBean(SessionCleanupJob.class);
        sessionCleanupJob.deleteExpiredSessions();

        Assertions.assertFalse(sessionRepository.existsById(expired.getId()));
        Assertions.assertTrue(sessionRepository.existsById(active.getId()));
    }
}