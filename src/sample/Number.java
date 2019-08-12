package sample;

import java.text.DecimalFormat;

public class Number extends EquationElement {

    DecimalFormat decimalFormat = new DecimalFormat("#.###");
    private double value;

    @Override
    public String toString() {
        return decimalFormat.format(value);
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
