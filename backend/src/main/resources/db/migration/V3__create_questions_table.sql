-- Create questions table
CREATE TABLE questions (
    question_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    test_id UUID NOT NULL REFERENCES tests(test_id) ON DELETE CASCADE,
    order_num INTEGER NOT NULL,
    question_text TEXT NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('SPEAKING', 'READING', 'LISTENING')),
    expected_duration INTEGER,
    keywords TEXT[],
    points INTEGER NOT NULL CHECK (points >= 0 AND points <= 100),
    UNIQUE(test_id, order_num)
);

-- Create index on test_id for faster lookups
CREATE INDEX idx_questions_test_id ON questions(test_id);

-- Add comment to table
COMMENT ON TABLE questions IS 'Stores individual questions for each test';
