package sample;

public abstract class Operator extends EquationElement {

    public abstract Number calc(Number a, Number b);

    public abstract Number calc(Number a);

    public boolean useOneArgument() {
        return false;
    }

    ;

    private Number a;
    private Number b;


    @Override
    public boolean isNumber() {
        return false;
    }

    public void setNumbers(Number a, Number b) {
        this.a = a;
        this.b = b;
    }



}
