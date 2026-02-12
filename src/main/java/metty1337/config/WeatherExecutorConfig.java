package metty1337.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherExecutorConfig {

  @Bean
  public ExecutorService weatherApiExecutor() {
    return Executors.newFixedThreadPool(10);
  }
}
