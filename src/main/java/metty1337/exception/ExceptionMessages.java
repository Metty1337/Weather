package metty1337.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessages {
  AUTHENTICATION_FAILED_EXCEPTION("Authentication failed."),
  USER_ALREADY_EXIST_EXCEPTION("User already exist."),
  USER_DOES_NOT_EXIST_EXCEPTION("User does not exist."),
  USER_DOES_NOT_AUTHORIZED_EXCEPTION("User does not authorized."),
  LOCATION_ALREADY_ADDED_EXCEPTION("Location already added."),
  WEATHER_CLIENT_EXCEPTION("Weather API client error:"),
  WEATHER_SERVER_EXCEPTION("Weather API server error:");
  private final String message;
}
