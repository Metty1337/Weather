package metty1337.exception;

public class LocationAlreadyAddedException extends RuntimeException {

  public LocationAlreadyAddedException(String message) {
    super(message);
  }

  public LocationAlreadyAddedException(String message, Throwable cause) {
    super(message, cause);
  }
}
