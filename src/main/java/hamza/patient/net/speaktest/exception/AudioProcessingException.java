package hamza.patient.net.speaktest.exception;

/**
 * Exception thrown when audio processing fails
 */
public class AudioProcessingException extends RuntimeException {
    public AudioProcessingException(String message) {
        super(message);
    }

    public AudioProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
