package metty1337.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class WeatherDto {

  private String name;
  private String country;
  private String temp;
  private String feelsLike;
  private String weather;
  private String humidity;
  private String icon;
  private String latitude;
  private String longitude;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public WeatherDto(
      @JsonProperty("name") String name,
      @JsonProperty("sys") Map<String, Object> sys,
      @JsonProperty("main") Map<String, Object> main,
      @JsonProperty("weather") List<Map<String, Object>> weather,
      @JsonProperty("coord") Map<String, Object> coordinates) {
    this.name = name;
    this.country = String.valueOf(sys.get("country"));
    this.temp = String.valueOf(main.get("temp"));
    this.feelsLike = String.valueOf(main.get("feels_like"));
    this.humidity = String.valueOf(main.get("humidity"));
    Map<String, Object> weatherConverted =
        (weather != null && !weather.isEmpty()) ? weather.getFirst() : Map.of();
    this.weather = String.valueOf(weatherConverted.get("description"));
    this.icon = String.valueOf(weatherConverted.get("icon"));
    this.latitude = String.valueOf(coordinates.get("lat"));
    this.longitude = String.valueOf(coordinates.get("lon"));
  }
}
