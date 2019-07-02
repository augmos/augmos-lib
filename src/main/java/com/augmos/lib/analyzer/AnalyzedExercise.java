package com.augmos.lib.analyzer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class AnalyzedExercise {

    public static final String ID_KEY = "id";
    public static final String STUDENT_KEY = "student";
    public static final String EXERCISE_KEY = "exercise";
    public static final String CORRECT_KEY = "correct";
    public static final String TARGET_KEY = "target";
    public static final String PRESENT_KEY = "present";
    public static final String MATHML_KEY = "mathml";
    public static final String JIIX_KEY = "jiix";

    // IDs
    private final long id;
    private final long student;
    private final long exercise;

    // analysis result
    private final boolean correct;
    private final int target;
    private final int present;

    // data
    private final String mathml;
    private final String jiix;

    @JsonCreator
    public AnalyzedExercise(
            @JsonProperty(ID_KEY) final long id,
            @JsonProperty(STUDENT_KEY) final long student,
            @JsonProperty(EXERCISE_KEY) final long exercise,
            @JsonProperty(CORRECT_KEY) final boolean correct,
            @JsonProperty(TARGET_KEY) final int target,
            @JsonProperty(PRESENT_KEY) final int present,
            @JsonProperty(MATHML_KEY) final String mathml,
            @JsonProperty(JIIX_KEY) final String jiix
    ) {
        this.id = id;
        this.student = student;
        this.exercise = exercise;
        this.correct = correct;
        this.target = target;
        this.present = present;
        this.mathml = mathml;
        this.jiix = jiix;
    }

    @JsonProperty(ID_KEY)
    public long getId() {
        return id;
    }

    @JsonProperty(STUDENT_KEY)
    public long getStudent() {
        return student;
    }

    @JsonProperty(EXERCISE_KEY)
    public long getExercise() {
        return exercise;
    }

    @JsonProperty(CORRECT_KEY)
    public boolean isCorrect() {
        return correct;
    }

    @JsonProperty(TARGET_KEY)
    public int getTarget() {
        return target;
    }

    @JsonProperty(PRESENT_KEY)
    public int getPresent() {
        return present;
    }

    @JsonProperty(MATHML_KEY)
    public String getMathml() {
        return mathml;
    }

    @JsonProperty(JIIX_KEY)
    public String getJiix() {
        return jiix;
    }
}
