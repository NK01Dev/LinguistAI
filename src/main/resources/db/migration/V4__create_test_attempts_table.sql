-- Create test_attempts table
CREATE TABLE test_attempts (
    attempt_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    test_id UUID NOT NULL REFERENCES tests(test_id) ON DELETE CASCADE,
    status VARCHAR(20) NOT NULL CHECK (status IN ('IN_PROGRESS', 'COMPLETED', 'ABANDONED')),
    started_at TIMESTAMP NOT NULL,
    completed_at TIMESTAMP,
    audio_url VARCHAR(500),
    transcription TEXT,
    total_score INTEGER CHECK (total_score >= 0 AND total_score <= 100),
    cefr_level VARCHAR(2) CHECK (cefr_level IN ('A1', 'A2', 'B1', 'B2', 'C1', 'C2')),
    fluency_score INTEGER CHECK (fluency_score >= 0 AND fluency_score <= 100),
    vocabulary_score INTEGER CHECK (vocabulary_score >= 0 AND vocabulary_score <= 100),
    grammar_score INTEGER CHECK (grammar_score >= 0 AND grammar_score <= 100),
    pronunciation_score INTEGER CHECK (pronunciation_score >= 0 AND pronunciation_score <= 100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create index on user_id for user history
CREATE INDEX idx_attempts_user_id ON test_attempts(user_id);

-- Create index on test_id for test analytics
CREATE INDEX idx_attempts_test_id ON test_attempts(test_id);

-- Create index on status for filtering
CREATE INDEX idx_attempts_status ON test_attempts(status);

-- Create composite index for user + status queries
CREATE INDEX idx_attempts_user_status ON test_attempts(user_id, status);

-- Add comment to table
COMMENT ON TABLE test_attempts IS 'Stores user test attempts with scores and analysis results';
