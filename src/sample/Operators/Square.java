package sample.Operators;

import sample.Number;
import sample.Operator;

public class Square extends Operator {
    private int priority = 3;
    @Override
    public Number calc(Number a, Number b) {
        return calc(a);
    }

    @Override
    public Number calc(Number a) {
        return new Number(Math.pow(a.getValue(), 2));
    }

    @Override
    public boolean useOneArgument() {
        return true;
    }

    @Override
    public String toString() {
        return "²";
    }
}
