package hamza.patient.net.speaktest.common.enums;

/**
 * Test attempt status
 */
public enum TestStatus {
    IN_PROGRESS("Test is currently in progress"),
    COMPLETED("Test has been completed"),
    ABANDONED("Test was started but not completed");

    private final String description;

    TestStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
