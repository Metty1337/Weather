package metty1337;

import java.time.Instant;
import metty1337.config.AppConfig;
import metty1337.config.TestConfig;
import metty1337.config.WebConfig;
import metty1337.dto.SignUpFormDto;
import metty1337.entity.Session;
import metty1337.entity.User;
import metty1337.job.SessionCleanupJob;
import metty1337.repository.LocationRepository;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitConfig
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class, TestConfig.class, WebConfig.class})
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "api_url=test-url",
    "api_key=test-api-key"
})
public class SessionServiceIntegrationTest {
  @Autowired private WebApplicationContext webApplicationContext;
  @Autowired private UserRepository userRepository;
  @Autowired private SessionRepository sessionRepository;
  @Autowired private LocationRepository locationRepository;

  @BeforeEach
  public void setUp() {
    locationRepository.deleteAll();
    sessionRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  public void shouldDeleteSessionAfterExpired() {
    SignUpFormDto signUpFormDto = new SignUpFormDto("Alex", "12345678", "12345678");
    UserService userService = webApplicationContext.getBean(UserService.class);
    User user = userService.createUser(signUpFormDto);

    SessionService sessionService = webApplicationContext.getBean(SessionService.class);

    Session expired = new Session(user, Instant.now().minusSeconds(350));
    Session active = new Session(user, Instant.now().plusSeconds(350));
    sessionService.createSession(expired);
    sessionService.createSession(active);

    SessionCleanupJob sessionCleanupJob = webApplicationContext.getBean(SessionCleanupJob.class);
    sessionCleanupJob.deleteExpiredSessions();

    Assertions.assertFalse(sessionRepository.existsById(expired.getId()));
    Assertions.assertTrue(sessionRepository.existsById(active.getId()));
  }
}
