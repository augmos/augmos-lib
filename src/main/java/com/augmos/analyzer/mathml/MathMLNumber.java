package com.augmos.analyzer.mathml;

public class MathMLNumber extends MathMLExpression<RealNumber> {

    private final RealNumber value;

    MathMLNumber(final RealNumber value) {
        this.value = value;
    }

    @Override
    RealNumber evaluate() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
