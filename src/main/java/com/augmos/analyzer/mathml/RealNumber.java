package com.augmos.analyzer.mathml;

public class RealNumber {
    private final int dividend;
    private final int divisor;
    private final RealNumberNotation realNumberNotation;

    public RealNumber(int dividend, int divisor, RealNumberNotation realNumberNotation){
        this.dividend = dividend;
        this.divisor = divisor;
        this.realNumberNotation = realNumberNotation;
    }

    public RealNumber add(RealNumber arg){
        int kgv = this.divisor * arg.divisor / gcd(this.divisor, arg.divisor);
        int div1 = this.dividend * (kgv / this.divisor);
        int div2 = arg.dividend * (kgv / arg.divisor);
        int newDiv = div1 + div2;
        // Get smallest basis
        newDiv = newDiv / gcd(newDiv, kgv);
        int newBasis = kgv / gcd(newDiv, kgv);

        return setNewNotation(newDiv, newBasis);
    }

    public RealNumber sub(RealNumber arg){
        //dividend negiert
        return this.add(new RealNumber(-arg.dividend, arg.divisor, arg.realNumberNotation));

    }

    public RealNumber div(RealNumber arg) {
        //argumente umgedreht
        return this.mul(new RealNumber(arg.divisor, arg.dividend, arg.realNumberNotation));
    }

    public RealNumber mul(RealNumber arg) {
        return setNewNotation(this.dividend * arg.dividend, this.divisor * arg.divisor);
    }

    public int compareTo(RealNumber arg) {
        double curr = this.dividend / this.divisor;
        double argD = arg.dividend / arg.divisor;
        if (curr == argD) {
            return 0;
        } else if (curr < argD) {
            return -1;
        }
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

    static private RealNumber setNewNotation(int div, int base) {
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
        FRACTION
    }
}
