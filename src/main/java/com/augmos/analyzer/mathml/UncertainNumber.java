package com.augmos.analyzer.mathml;

import static com.augmos.analyzer.mathml.RealNumber.RealNumberNotation.UNCERTAIN;

/**
 * This class represents a case of evaluation uncertainty. If for example an expression x+2 would be evaluated without
 * assigning a value to x, x+2 would be uncertain.
 */

public class UncertainNumber extends RealNumber {

    private static UncertainNumber instance;

    public static UncertainNumber get() {
        if (instance == null)
            instance = new UncertainNumber();
        return instance;
    }

    private UncertainNumber() {
        super(0, 0, UNCERTAIN);
    }

    @Override
    public RealNumber add(final RealNumber arg) {
        return instance;
    }

    @Override
    public RealNumber sub(final RealNumber arg) {
        return instance;
    }

    @Override
    public RealNumber mul(final RealNumber arg) {
        return instance;
    }

    @Override
    public RealNumber div(final RealNumber arg) {
        return instance;
    }
}
