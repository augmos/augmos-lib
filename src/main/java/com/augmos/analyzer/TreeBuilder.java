package com.augmos.analyzer;

import java.util.Map;

public interface TreeBuilder<T> {

    AnalysisResult build(Map<Expression, Evaluation> content);

}
