package com.augmos.analyzer;

import java.util.List;
import java.util.Map;

public interface Evaluator<T> {

    Map<Expression, Evaluation> evaluate(List<T> expressions);

}
