package com.augmos.analyzer.mathml;

/**
 * This class represents and emulates the behavior of variables in an expression. If not set, they will be evaluated as
 * UncertainNumbers, which will propagate to the top of the Expression.
 */

public class MathMLParam extends MathMLExpression<RealNumber> {

    private final String key;
    private RealNumber value;

    public MathMLParam(final String key) {
        this.key = key;
        this.value = UncertainNumber.get();
    }

    void set(final RealNumber value) {
        this.value = value;
    }

    void reset() {
        this.value = UncertainNumber.get();
    }

    @Override
    RealNumber evaluate() {
        return value;
    }
}
