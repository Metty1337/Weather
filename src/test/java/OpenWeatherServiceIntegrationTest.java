import lombok.extern.slf4j.Slf4j;
import metty1337.config.AppConfig;
import metty1337.config.TestConfig;
import metty1337.config.WebConfig;
import metty1337.dto.SignUpFormDto;
import metty1337.dto.WeatherDto;
import metty1337.entity.Location;
import metty1337.entity.User;
import metty1337.repository.LocationRepository;
import metty1337.service.OpenWeatherService;
import metty1337.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

@Slf4j
@SpringJUnitConfig
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class, TestConfig.class, WebConfig.class})
@ActiveProfiles("test")
public class OpenWeatherServiceIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void test() {
        LocationRepository locationRepository = webApplicationContext.getBean(LocationRepository.class);
        UserService userService = webApplicationContext.getBean(UserService.class);
        User user = userService.createUser(new SignUpFormDto("Alex", "12345678", "12345678"));

        Location location = new Location("London", user, BigDecimal.valueOf(51.5085), BigDecimal.valueOf(-0.1257));
        location = locationRepository.save(location);

        OpenWeatherService openWeatherService = webApplicationContext.getBean(OpenWeatherService.class);
        WeatherDto weatherDto = openWeatherService.getWeatherByCoords(location);
        log.info(weatherDto.toString());
        Assertions.assertNotNull(weatherDto);
    }
}
