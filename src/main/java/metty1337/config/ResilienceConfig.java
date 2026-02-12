package metty1337.config;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.spring6.ratelimiter.configure.RateLimiterConfiguration;
import io.github.resilience4j.spring6.ratelimiter.configure.RateLimiterConfigurationProperties;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAspectJAutoProxy
@Import({RateLimiterConfiguration.class})
public class ResilienceConfig {

  @Bean
  public RateLimiterRegistry rateLimiterRegistry() {
    RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
        .limitRefreshPeriod(Duration.ofSeconds(60))
        .limitForPeriod(60)
        .timeoutDuration(Duration.ZERO)
        .build();
    return RateLimiterRegistry.of(rateLimiterConfig);
  }

  @Bean
  public RateLimiterConfigurationProperties rateLimiterConfigurationProperties() {
    return new RateLimiterConfigurationProperties();
  }
}
