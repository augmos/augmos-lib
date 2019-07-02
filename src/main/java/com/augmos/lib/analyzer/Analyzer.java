package com.augmos.lib.analyzer;

import com.augmos.lib.databind.beans.RenderedExercise;

public interface Analyzer<T> {

    public AnalyzedExercise analyze(long id, long student, T exercise, String mathml, String jiix);

}
