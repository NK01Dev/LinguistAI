package hamza.patient.net.speaktest.common.constants;

/**
 * Application-wide constants
 */
public final class AppConstants {

    private AppConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // API Versioning
    public static final String API_V1 = "/api/v1";

    // Pagination
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
    public static final String DEFAULT_SORT_BY = "createdAt";
    public static final String DEFAULT_SORT_DIRECTION = "DESC";

    // Audio Processing
    public static final int MAX_AUDIO_FILE_SIZE_MB = 10;
    public static final long MAX_AUDIO_FILE_SIZE_BYTES = MAX_AUDIO_FILE_SIZE_MB * 1024 * 1024;
    public static final String[] ALLOWED_AUDIO_FORMATS = { "mp3", "wav", "m4a", "webm" };
    public static final int AUDIO_RETENTION_DAYS = 7;

    // Test Configuration
    public static final int MIN_TEST_DURATION_MINUTES = 5;
    public static final int MAX_TEST_DURATION_MINUTES = 60;
    public static final int MIN_QUESTIONS_PER_TEST = 1;
    public static final int MAX_QUESTIONS_PER_TEST = 50;

    // Scoring
    public static final int MIN_SCORE = 0;
    public static final int MAX_SCORE = 100;
    public static final int PASSING_SCORE = 60;

    // JWT
    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_PREFIX = "Bearer ";

    // Date/Time Formats
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
}
