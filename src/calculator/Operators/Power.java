package calculator.Operators;

import calculator.Number;
import calculator.Operator;

public class Power extends Operator {

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public Number calc(Number a, Number b) {
        return new Number(Math.pow(a.getValue(), b.getValue()));
    }

    @Override
    public Number calc(Number a) {
        return null;
    }

    @Override
    public String toString() {
        return "^";
    }
}
