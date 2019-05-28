package com.augmos.analyzer.mathml;

import com.augmos.analyzer.Expression;

/**
 * A representation of a functionally parsed mathematical expression.
 *
 * @param <T> the type of expression evaluation result (RealNumber, Boolean, ...)
 */

public abstract class MathMLExpression<T> extends Expression<T> {

    abstract T evaluate();

}
