package calculator;

public abstract class Operator extends EquationElement {

    public abstract Number calc(Number a, Number b);

    public abstract Number calc(Number a);

    public boolean useOneArgument() {
        return false;
    }

    public int getPriority() {
        return priority;
    }

    private int priority;

    public void addPriority(int i) {
        priority += i;
    }


    @Override
    public boolean isNumber() {
        return false;
    }



}
