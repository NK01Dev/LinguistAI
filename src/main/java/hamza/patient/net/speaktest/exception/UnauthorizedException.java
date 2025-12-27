package hamza.patient.net.speaktest.exception;

/**
 * Exception thrown for unauthorized access attempts
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
