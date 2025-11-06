package co.founders.auth.exception;

public class NotAuthenticationException extends RuntimeException {
    public NotAuthenticationException(String message) {
        super(message);
    }
}
