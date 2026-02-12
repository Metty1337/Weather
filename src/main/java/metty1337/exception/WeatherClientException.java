package metty1337.exception;

public class WeatherClientException extends RuntimeException {

  public WeatherClientException(String message) {
    super(message);
  }

  public WeatherClientException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
