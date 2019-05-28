package com.augmos.analyzer.mathml;

public class RealNumber {

    /**
     * A universal representation of written numbers as a real number
     * A real number can be represented as a/b, where a and b are integers
     *
     * realNumberNotation shows how the number was/has to be written in text (integer, float or fraction)
     */

    private final int dividend;
    private final int divisor;
    private final RealNumberNotation realNumberNotation;

    /**
     * A default constructor that automatically reduces the parameters for the numbers to be minimal
     *
     * @param dividend
     * @param divisor
     * @param realNumberNotation
     */
    public RealNumber(
            final int dividend,
            final int divisor,
            final RealNumberNotation realNumberNotation
    ){
        final int gcd = gcd(dividend, divisor);
        this.dividend = dividend / gcd;
        this.divisor = divisor / gcd;
        this.realNumberNotation = realNumberNotation;
    }

    public RealNumber add(final RealNumber arg){
        if (RealNumberNotation.UNCERTAIN.equals(arg.realNumberNotation))
            return UncertainNumber.get();

        final int scm = scm(this.divisor, arg.divisor);
        int newDiv = this.dividend * (scm / this.divisor) + arg.dividend * (scm / arg.divisor);
        return create(newDiv, scm);
    }

    public RealNumber sub(RealNumber arg){
        if (RealNumberNotation.UNCERTAIN.equals(arg.realNumberNotation))
            return UncertainNumber.get();

        final int scm = scm(this.divisor, arg.divisor);
        int newDiv = this.dividend * (scm / this.divisor) - arg.dividend * (scm / arg.divisor);
        return create(newDiv, scm);
    }

    public RealNumber mul(RealNumber arg) {
        if (RealNumberNotation.UNCERTAIN.equals(arg.realNumberNotation))
            return UncertainNumber.get();

        return create(this.dividend * arg.dividend, this.divisor * arg.divisor);
    }

    public RealNumber div(RealNumber arg) {
        if (RealNumberNotation.UNCERTAIN.equals(arg.realNumberNotation))
            return UncertainNumber.get();

        return create(this.dividend * arg.divisor, this.divisor * arg.dividend);
    }

    /**
     * Compares two numbers. Always assumes that the numbers are in their reduced form
     * @param arg
     * @return -1 if this is lesser than arg, 0 if this=arg, 1 if this is greater than arg
     */
    public int compareTo(final RealNumber arg) {
        final int a = this.dividend * arg.divisor;
        final int b = arg.dividend * this.divisor;

        if (a == b)
            return 0;
        if (a < b)
            return -1;
        else
            return 1;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final RealNumber that = (RealNumber) obj;
        int kgv = this.divisor * that.divisor / gcd(this.divisor, that.divisor);
        int div1 = this.dividend * (kgv / this.divisor);
        int div2 = that.dividend * (kgv / that.divisor);
        return div1 == div2;
    }

    @Override
    public String toString() {
        switch (realNumberNotation){
            case FLOAT: return Float.toString(dividend / (float) divisor);
            case INTEGER: return Integer.toString(dividend);
            case FRACTION:
                return dividend + " / " + divisor; //TODO später ggf anpassen
        }
        return super.toString();
    }

    //private helping methods

    static private int gcd(int a, int b) {
        while (a != b) {
            if (a > b) a -= b;
            else b -= a;
        }
        return a;
    }

    static private int scm(
            final int a,
            final int b
    ) {
        return (a * b) / gcd(a,b);
    }

    /**
     * A private constructor that automatically sets notation types
     *
     * @param div
     * @param base
     * @return
     */
    static private RealNumber create(int div, int base) {
        //Check if Integer and return
        if (div % base == 0) {
            return new RealNumber(div / base, 1, RealNumberNotation.INTEGER);
        }
        //2,4,5,10 als basis zu float
        if (base == 2 || base == 4 || base == 5 || base % 10 == 0) {
            return new RealNumber(div, base, RealNumberNotation.FLOAT);
        }

        return new RealNumber(div, base, RealNumberNotation.FRACTION);
    }

    enum RealNumberNotation{
        INTEGER,
        FLOAT,
        FRACTION,
        UNCERTAIN
    }
}
