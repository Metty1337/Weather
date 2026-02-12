package metty1337.exception;

public class WeatherServerException extends RuntimeException {

  public WeatherServerException(String message) {
    super(message);
  }

  public WeatherServerException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
