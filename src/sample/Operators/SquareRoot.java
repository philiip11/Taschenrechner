package sample.Operators;

import sample.Number;
import sample.Operator;

public class SquareRoot extends Operator {
    @Override
    public Number calc(Number a, Number b) {
        return calc(a);
    }

    @Override
    public Number calc(Number a) {
        return new Number(Math.sqrt(a.getValue()));
    }

    @Override
    public boolean useOneArgument() {
        return true;
    }

    @Override
    public String toString() {
        return null;
    }
}
