package calculator.Operators;

import calculator.Number;
import calculator.Operator;

public class Divide extends Operator {

    @Override
    public int getPriority() {
        return 2;
    }
    @Override
    public Number calc(Number a, Number b) {
        return new Number(a.getValue() / b.getValue());
    }

    @Override
    public Number calc(Number a) {
        return null;
    }

    @Override
    public String toString() {
        return "÷";
    }
}
