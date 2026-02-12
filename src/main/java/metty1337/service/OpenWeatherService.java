package metty1337.service;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import java.math.BigDecimal;
import java.util.List;
import metty1337.dto.LocationDto;
import metty1337.dto.WeatherDto;
import metty1337.exception.ExceptionMessages;
import metty1337.exception.TooManyRequestException;
import metty1337.exception.WeatherClientException;
import metty1337.exception.WeatherServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OpenWeatherService {

  private static final String FIND_WEATHER_REQUEST_URI = "/data/2.5/weather";
  private static final String FIND_LOCATIONS_REQUEST_URI = "/geo/1.0/direct";
  private static final String LATITUDE_PARAM = "lat";
  private static final String LONGITUDE_PARAM = "lon";
  private static final String METRIC_SYSTEM = "metric";
  private static final String API_KEY_PARAM = "appid";
  private static final String UNITS_PARAM = "units";
  private static final String NAME_PARAM = "q";
  private static final int MAX_OF_POSSIBLE_LOCATIONS = 5;
  private static final String LIMIT_ATTR = "limit";
  private final RestClient restClient;
  private final String apiKey;

  public OpenWeatherService(RestClient restClient, @Value("${api_key}") String apiKey) {
    this.restClient = restClient;
    this.apiKey = apiKey;
  }

  @RateLimiter(name = "openweather", fallbackMethod = "rateLimitFallback")
  public WeatherDto getWeatherByCoords(BigDecimal latitude, BigDecimal longitude) {
    return restClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path(FIND_WEATHER_REQUEST_URI)
                    .queryParam(LATITUDE_PARAM, latitude)
                    .queryParam(LONGITUDE_PARAM, longitude)
                    .queryParam(API_KEY_PARAM, apiKey)
                    .queryParam(UNITS_PARAM, METRIC_SYSTEM)
                    .build())
        .retrieve()
        .onStatus(
            HttpStatusCode::is4xxClientError,
            (request, response) -> {
              throw new WeatherClientException(
                  ExceptionMessages.WEATHER_CLIENT_EXCEPTION.getMessage()
                      + response.getStatusCode());
            })
        .onStatus(
            HttpStatusCode::is5xxServerError,
            (request, response) -> {
              throw new WeatherServerException(
                  ExceptionMessages.WEATHER_SERVER_EXCEPTION.getMessage()
                      + response.getStatusCode());
            })
        .body(WeatherDto.class);
  }

  @RateLimiter(name = "openweather", fallbackMethod = "rateLimitFallback")
  public List<LocationDto> getLocationsByName(String name) {
    return restClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path(FIND_LOCATIONS_REQUEST_URI)
                    .queryParam(NAME_PARAM, name)
                    .queryParam(LIMIT_ATTR, MAX_OF_POSSIBLE_LOCATIONS)
                    .queryParam(API_KEY_PARAM, apiKey)
                    .build())
        .retrieve()
        .onStatus(
            HttpStatusCode::is4xxClientError,
            (request, response) -> {
              throw new WeatherClientException(
                  ExceptionMessages.WEATHER_CLIENT_EXCEPTION.getMessage()
                      + response.getStatusCode());
            })
        .onStatus(
            HttpStatusCode::is5xxServerError,
            (request, response) -> {
              throw new WeatherServerException(
                  ExceptionMessages.WEATHER_SERVER_EXCEPTION.getMessage()
                      + response.getStatusCode());
            })
        .body(new ParameterizedTypeReference<>() {
        });
  }

  private WeatherDto rateLimitFallback(BigDecimal latitude, BigDecimal longitude, Throwable t) {
    if (t instanceof RequestNotPermitted) {
      throw new TooManyRequestException(
          ExceptionMessages.TOO_MANY_REQUEST_EXCEPTION.getMessage(), t);
    }
    if (t instanceof RuntimeException re) {
      throw re;
    }
    throw new RuntimeException(t);
  }

  private List<LocationDto> rateLimitFallback(String name, Throwable t) {
    if (t instanceof RequestNotPermitted) {
      throw new TooManyRequestException(
          ExceptionMessages.TOO_MANY_REQUEST_EXCEPTION.getMessage(), t);
    }
    if (t instanceof RuntimeException re) {
      throw re;
    }
    throw new RuntimeException(t);
  }

}
