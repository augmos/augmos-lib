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

    static private int gcd(int a, int b) {
        while (a != b) {
            if (a > b) a -= b;
            else b -= a;
        }
        return a;
    }

    public RealNumber add(RealNumber arg){
        int kgv = this.divisor * arg.divisor / gcd(this.divisor, arg.divisor);
        int div1 = this.dividend * (kgv / this.divisor);
        int div2 = arg.dividend * (kgv / arg.divisor);
        int newDiv = div1 + div2;
        // Get smallest basis
        newDiv = newDiv / gcd(newDiv, kgv);
        int newBasis = kgv / gcd(newDiv, kgv);

        //Check if Integer and return
        if(newDiv % newBasis == 0){
            return new RealNumber(newDiv / newBasis, 1, RealNumberNotation.INTEGER);
        }
        //2,4,5,10 als basis zu float
        if(newBasis == 2 || newBasis == 4 || newBasis == 5 || newBasis % 10 == 0){
            return new RealNumber(newDiv, newBasis, RealNumberNotation.FLOAT);
        }

        return new RealNumber(newDiv, newBasis, RealNumberNotation.FRACTION);

    }

    public RealNumber sub(RealNumber arg){
        int kgv = this.divisor * arg.divisor / gcd(this.divisor, arg.divisor);
        int div1 = this.dividend * (kgv / this.divisor);
        int div2 = arg.dividend * (kgv / arg.divisor);
        int newDiv = div1 - div2;
        // Get smallest basis
        newDiv = newDiv / gcd(newDiv, kgv);
        int newBasis = kgv / gcd(newDiv, kgv);

        //Check if Integer and return
        if(newDiv % newBasis == 0){
            return new RealNumber(newDiv / newBasis, 1, RealNumberNotation.INTEGER);
        }
        //2,4,5,10 als basis zu float
        if(newBasis == 2 || newBasis == 4 || newBasis == 5 || newBasis % 10 == 0){
            return new RealNumber(newDiv, newBasis, RealNumberNotation.FLOAT);
        }

        return new RealNumber(newDiv, newBasis, RealNumberNotation.FRACTION);

    }
    public RealNumber div(RealNumber arg){

    }
    public RealNumber mul(RealNumber arg){

    }
    public int compareTo(RealNumber arg){

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final RealNumber that = (RealNumber) obj;
        //TODO
        return super.equals(obj);
    }

    @Override
    public String toString() {
        switch (realNumberNotation){
            case FLOAT: return Float.toString(dividend / (float) divisor);
            case INTEGER: return Integer.toString(dividend);
            case FRACTION: return Integer.toString(dividend) + " / " + Integer.toString(divisor); //TODO später ggf anpassen
        }
        return super.toString();
    }

    enum RealNumberNotation{
        INTEGER,
        FLOAT,
        FRACTION
    }
}
