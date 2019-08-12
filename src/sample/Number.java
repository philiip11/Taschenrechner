package sample;

public class Number extends EquationElement {

    private double value;

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Number(double value) {
        this.value = value;
    }
}
