-- Create tests table
CREATE TABLE tests (
    test_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(200) NOT NULL,
    description TEXT,
    language VARCHAR(2) NOT NULL CHECK (language IN ('EN', 'FR')),
    target_level VARCHAR(2) NOT NULL CHECK (target_level IN ('A1', 'A2', 'B1', 'B2', 'C1', 'C2')),
    duration INTEGER NOT NULL CHECK (duration >= 5 AND duration <= 60),
    question_count INTEGER NOT NULL CHECK (question_count >= 1 AND question_count <= 50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create index on language for filtering
CREATE INDEX idx_tests_language ON tests(language);

-- Create index on target_level for filtering
CREATE INDEX idx_tests_target_level ON tests(target_level);

-- Add comment to table
COMMENT ON TABLE tests IS 'Stores test metadata and configuration';
