package calculator;

public abstract class Operator extends EquationElement {

    public abstract int getPriority();

    public abstract Number calc(Number a, Number b);

    public abstract Number calc(Number a);

    public boolean useOneArgument() {
        return false;
    }


    @Override
    public boolean isNumber() {
        return false;
    }


}
