package calculator.Operators;

import calculator.Number;
import calculator.Operator;


public class Percentage extends Operator {

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public Number calc(Number a, Number b) {
        return new Number((a.getValue() / 100 * b.getValue()));
    }
    //TODO
    @Override
    public Number calc(Number a) {    //%[Number] --> Caused by: java.lang.ArrayIndexOutOfBoundsException: -1
        return new Number(a.getValue() / 100);
    }

    @Override
    public String toString() {
        return "%";
    }
}

