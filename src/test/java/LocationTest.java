import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import metty1337.config.AppConfig;
import metty1337.config.TestConfig;
import metty1337.config.WebConfig;
import metty1337.dto.LocationDto;
import metty1337.dto.SignUpFormDto;
import metty1337.entity.Location;
import metty1337.entity.User;
import metty1337.mapper.LocationMapper;
import metty1337.repository.LocationRepository;
import metty1337.repository.SessionRepository;
import metty1337.repository.UserRepository;
import metty1337.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@SpringJUnitConfig
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class, TestConfig.class, WebConfig.class})
@ActiveProfiles("test")
public class LocationTest {

  @Autowired
  private WebApplicationContext webApplicationContext;
  @Autowired
  private LocationMapper locationMapper;
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
  public void testLocationMapper() {
    LocationDto locationDto = LocationDto.builder()
        .name("London")
        .latitude("51.5085")
        .longitude("-0.1257")
        .country("GB")
        .state("England")
        .build();
    Location location = locationMapper.dtoToLocation(locationDto);
    log.info(location.toString());
    Assertions.assertNotNull(location);
  }

  @Test
  public void shouldThrowUniqueConstraintException() {
    SignUpFormDto signUpFormDto = new SignUpFormDto("Alex", "12345678", "12345678");
    UserService userService = webApplicationContext.getBean(UserService.class);
    User user = userService.createUser(signUpFormDto);

    LocationRepository locationRepository = webApplicationContext.getBean(LocationRepository.class);
    Location location = new Location();
    location.setName("London");
    location.setUser(user);
    location.setLongitude(BigDecimal.valueOf(-0.1257));
    location.setLatitude(BigDecimal.valueOf(51.5085));
    location = locationRepository.save(location);
    log.info(location.toString());

    Location duplicate = new Location();

    duplicate.setName("London");
    duplicate.setUser(user);
    duplicate.setLongitude(BigDecimal.valueOf(-0.1257));
    duplicate.setLatitude(BigDecimal.valueOf(51.5085));

    Assertions.assertThrows(DataIntegrityViolationException.class,
        () -> locationRepository.saveAndFlush(duplicate));
  }
}
