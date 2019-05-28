package com.augmos.analyzer.mathml;

public class MathMLBinOp extends MathMLExpression<RealNumber> {

    private final BinOpType type;
    private final MathMLExpression<RealNumber> arg1;
    private final MathMLExpression<RealNumber> arg2;

    public MathMLBinOp(
            final BinOpType type,
            final MathMLExpression<RealNumber> arg1,
            final MathMLExpression<RealNumber> arg2) {
        this.type = type;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    RealNumber evaluate() {
        switch (type) {
            case ADD : {
                return arg1.evaluate().add(arg2.evaluate());
            }

            case SUB : {
                return arg1.evaluate().sub(arg2.evaluate());
            }

            case MUL : {
                return arg1.evaluate().mul(arg2.evaluate());
            }

            case DIV : {
                return arg1.evaluate().div(arg2.evaluate());
            }
        }

        return null;
    }

    enum BinOpType {
        ADD,
        SUB,
        MUL,
        DIV
    }
}
