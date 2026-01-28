package metty1337.exception;

public class UserDoesNotExistException extends RuntimeException {

  public UserDoesNotExistException(String message) {
    super(message);
  }

  public UserDoesNotExistException(String message, Throwable cause) {
  }
}
