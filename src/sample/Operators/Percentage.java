package sample.Operators;

import sample.Number;
import sample.Operator;

//TODO
public class Percentage extends Operator {
    private int priority = 2;
    @Override
    public Number calc(Number a, Number b) {
        return null;
    }

    @Override
    public Number calc(Number a) {
        return null;
    }

    @Override
    public String toString() {
        return "%";
    }
}
