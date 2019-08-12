package sample.Operators;

import sample.Number;
import sample.Operator;

public class Add extends Operator {
    @Override
    public Number calc(Number a, Number b) {
        return new Number(a.getValue() + b.getValue());
    }

    @Override
    public Number calc(Number a) {
        return null;
    }

    @Override
    public String toString() {
        return "+";
    }
}
