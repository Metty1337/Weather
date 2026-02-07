package metty1337;

import metty1337.config.AppConfig;
import metty1337.config.TestConfig;
import metty1337.config.WebConfig;
import metty1337.dto.SignUpFormDto;
import metty1337.exception.UserAlreadyExistException;
import metty1337.repository.LocationRepository;
import metty1337.repository.SessionRepository;
import metty1337.repository.UserRepository;
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
public class UserServiceIntegrationTest {

  @Autowired
  private WebApplicationContext webApplicationContext;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private SessionRepository sessionRepository;
  @Autowired
  private LocationRepository locationRepository;

  @BeforeEach
  public void setUp() {
    locationRepository.deleteAll();
    sessionRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  public void shouldCreateAndFindUser() {
    SignUpFormDto signUpFormDto = new SignUpFormDto("Alex", "12345678", "12345678");
    String username = signUpFormDto.getUsername();

    UserService userService = webApplicationContext.getBean(UserService.class);
    userService.createUser(signUpFormDto);

    Assertions.assertTrue(userService.findByLogin(username).isPresent());
  }

  @Test
  public void shouldThrowExceptionWhenUserAlreadyExists() {
    SignUpFormDto signUpFormDto = new SignUpFormDto("Alex", "12345678", "12345678");
    UserService userService = webApplicationContext.getBean(UserService.class);
    userService.createUser(signUpFormDto);

    Assertions.assertThrows(
        UserAlreadyExistException.class, () -> userService.createUser(signUpFormDto));
  }
}
