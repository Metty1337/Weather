package metty1337.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessages {
    AUTHENTICATION_FAILED_EXCEPTION("Authentication failed.");

    private final String message;
}
