package hamza.patient.net.speaktest.common.enums;

/**
 * CEFR (Common European Framework of Reference for Languages) proficiency
 * levels
 */
public enum CefrLevel {
    A1("Beginner", 0, 20),
    A2("Elementary", 21, 40),
    B1("Intermediate", 41, 60),
    B2("Upper Intermediate", 61, 75),
    C1("Advanced", 76, 90),
    C2("Proficient", 91, 100);

    private final String description;
    private final int minScore;
    private final int maxScore;

    CefrLevel(String description, int minScore, int maxScore) {
        this.description = description;
        this.minScore = minScore;
        this.maxScore = maxScore;
    }

    public String getDescription() {
        return description;
    }

    public int getMinScore() {
        return minScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    /**
     * Determine CEFR level based on total score
     */
    public static CefrLevel fromScore(int score) {
        for (CefrLevel level : values()) {
            if (score >= level.minScore && score <= level.maxScore) {
                return level;
            }
        }
        // Default to A1 for very low scores
        return A1;
    }
}
