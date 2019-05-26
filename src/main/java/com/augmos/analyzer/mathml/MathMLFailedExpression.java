package com.augmos.analyzer.mathml;

import com.augmos.analyzer.Expression;

public class MathMLFailedExpression extends Expression {
    private final int ErrorLocation;
    private final int ErrorLength;
    private final String ErrorContent;
    private final String ErrorMsg;

    public MathMLFailedExpression(int ErrorLocation, int ErrorLength, String ErrorMsg, String ErrorContent){
        this.ErrorLocation = ErrorLocation;
        this.ErrorLength = ErrorLength;
        this.ErrorMsg = ErrorMsg;
        this.ErrorContent = ErrorContent;
    }
}
