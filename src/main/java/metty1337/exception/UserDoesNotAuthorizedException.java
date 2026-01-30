package metty1337.exception;

public class UserDoesNotAuthorizedException extends RuntimeException {

  public UserDoesNotAuthorizedException(String message) {
    super(message);
  }

  public UserDoesNotAuthorizedException(String message, Throwable cause) {
    super(message, cause);
  }
}
