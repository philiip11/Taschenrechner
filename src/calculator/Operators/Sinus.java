package calculator.Operators;

import calculator.Number;
import calculator.Operator;

public class Sinus extends Operator {

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public Number calc(Number a, Number b) {
        return calc(a);
    }

    @Override
    public Number calc(Number a) {
        return new Number(Math.sin(a.getValue()));
    }

    @Override
    public boolean useOneArgument() {
        return true;
    }

    @Override
    public String toString() {
        return "sin";
    }
}
