package com.augmos.analyzer;

import java.util.Collections;
import java.util.List;

public abstract class AnalysisResult {
    
    private final boolean parsed;
    private final boolean valid;
    private final boolean correct;
    private final List<UIFeedbackInstruction> instructions;

    AnalysisResult(
            final boolean parsed,
            final boolean valid,
            final boolean correct,
            final List<UIFeedbackInstruction> instructions) {
        this.parsed = parsed;
        this.valid = valid;
        this.correct = correct;
        this.instructions = Collections.unmodifiableList(instructions);
    }

    public final boolean isParsed() {
        return parsed;
    }

    public final boolean isValid() {
        return valid;
    }

    public final boolean isCorrect() {
        return correct;
    }

    public final List<UIFeedbackInstruction> getInstructions() {
        return instructions;
    }
}
