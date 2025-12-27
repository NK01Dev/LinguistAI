package hamza.patient.net.speaktest.common.constants;

/**
 * Error message templates
 */
public final class ErrorMessages {

    private ErrorMessages() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // Authentication & Authorization
    public static final String INVALID_CREDENTIALS = "Invalid username or password";
    public static final String USER_ALREADY_EXISTS = "User with this email already exists";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String UNAUTHORIZED_ACCESS = "You are not authorized to access this resource";
    public static final String TOKEN_EXPIRED = "Authentication token has expired";
    public static final String INVALID_TOKEN = "Invalid authentication token";

    // Test Management
    public static final String TEST_NOT_FOUND = "Test not found with id: %s";
    public static final String QUESTION_NOT_FOUND = "Question not found with id: %s";
    public static final String TEST_ALREADY_EXISTS = "Test with this title already exists";
    public static final String INVALID_TEST_DURATION = "Test duration must be between %d and %d minutes";
    public static final String INVALID_QUESTION_COUNT = "Question count must be between %d and %d";

    // Test Attempts
    public static final String ATTEMPT_NOT_FOUND = "Test attempt not found with id: %s";
    public static final String ATTEMPT_ALREADY_COMPLETED = "This test attempt has already been completed";
    public static final String ATTEMPT_NOT_IN_PROGRESS = "Test attempt is not in progress";

    // Audio Processing
    public static final String AUDIO_FILE_TOO_LARGE = "Audio file size exceeds maximum allowed size of %d MB";
    public static final String INVALID_AUDIO_FORMAT = "Invalid audio format. Allowed formats: %s";
    public static final String AUDIO_UPLOAD_FAILED = "Failed to upload audio file";
    public static final String TRANSCRIPTION_FAILED = "Failed to transcribe audio";
    public static final String AUDIO_NOT_FOUND = "Audio file not found";

    // Validation
    public static final String VALIDATION_ERROR = "Validation failed";
    public static final String REQUIRED_FIELD = "%s is required";
    public static final String INVALID_EMAIL = "Invalid email format";
    public static final String PASSWORD_TOO_SHORT = "Password must be at least 8 characters long";
    public static final String INVALID_LANGUAGE = "Invalid language code: %s";
    public static final String INVALID_CEFR_LEVEL = "Invalid CEFR level: %s";

    // General
    public static final String INTERNAL_SERVER_ERROR = "An internal server error occurred";
    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    public static final String BAD_REQUEST = "Bad request";
}
