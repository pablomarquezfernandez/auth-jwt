package co.founders.auth.exception;

public class InvalidCodeException extends RuntimeException {
    public InvalidCodeException(String message) {
        super(message);
    }
}
