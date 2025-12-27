package hamza.patient.net.speaktest.common.enums;

/**
 * Question types for tests
 */
public enum QuestionType {
    SPEAKING("Speaking question requiring audio response"),
    READING("Reading comprehension question"),
    LISTENING("Listening comprehension question");

    private final String description;

    QuestionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
