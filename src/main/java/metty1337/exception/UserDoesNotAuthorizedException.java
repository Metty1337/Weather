package metty1337.exception;

public class UserDoesNotAuthorizedException extends RuntimeException {

  public UserDoesNotAuthorizedException(String message) {
    super(message);
  }
}
