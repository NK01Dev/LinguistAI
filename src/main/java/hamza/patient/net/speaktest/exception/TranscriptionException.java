package hamza.patient.net.speaktest.exception;

/**
 * Exception thrown when transcription fails
 */
public class TranscriptionException extends RuntimeException {
    public TranscriptionException(String message) {
        super(message);
    }

    public TranscriptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
