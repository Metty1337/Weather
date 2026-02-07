package metty1337.exception;

public class UserAlreadyExistException extends RuntimeException {

  public UserAlreadyExistException(String message, Throwable cause) {
    super(message, cause);
  }
}
