package calculator.Operators;

import calculator.Number;
import calculator.Operator;

public class Inverse extends Operator {

    @Override
    public int getPriority() {
        return 2;
    }
    @Override
    public Number calc(Number a, Number b) {
        return calc(b);
    }

    @Override
    public Number calc(Number a) {
        return new Number(1.0 / a.getValue());
    }

    @Override
    public boolean useOneArgument() {
        return true;
    }

    @Override
    public String toString() {
        return "⅟";
    }
}
