package metty1337.service;

import java.math.BigDecimal;
import metty1337.dto.WeatherDto;
import metty1337.entity.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OpenWeatherService {

  private static final String URL_FIND_REQUEST = "/data/2.5/weather";
  private static final String LATITUDE_PARAM = "lat";
  private static final String LONGITUDE = "lon";
  private static final String METRIC_SYSTEM = "metric";
  private static final String API_KEY_PARAM = "appid";
  private static final String UNITS_PARAM = "units";
  private static final String CITY_PARAM = "q";
  private final RestClient restClient;
  private final String apiKey;

  public OpenWeatherService(RestClient restClient, @Value("${api_key}") String apiKey) {
    this.restClient = restClient;
    this.apiKey = apiKey;
  }

  public WeatherDto getWeatherByCoords(Location location) {
    BigDecimal latitude = location.getLatitude();
    BigDecimal longitude = location.getLongitude();
    return restClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path(URL_FIND_REQUEST)
                    .queryParam(LATITUDE_PARAM, latitude)
                    .queryParam(LONGITUDE, longitude)
                    .queryParam(API_KEY_PARAM, apiKey)
                    .queryParam(UNITS_PARAM, METRIC_SYSTEM)
                    .build())
        .retrieve()
        .body(WeatherDto.class);
  }

  public WeatherDto getWeatherByCity(Location location) {
    String city = location.getName();
    return restClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path(URL_FIND_REQUEST)
                    .queryParam(CITY_PARAM, city)
                    .queryParam(API_KEY_PARAM, apiKey)
                    .queryParam(UNITS_PARAM, METRIC_SYSTEM)
                    .build())
        .retrieve()
        .body(WeatherDto.class);
  }
}
