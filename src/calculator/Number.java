package calculator;

import java.text.DecimalFormat;

public class Number extends EquationElement {

    private DecimalFormat decimalFormat = new DecimalFormat("#.###");
    private double value;
    private String name;

    @Override
    public String toString() {
        if (name == null) {
            return decimalFormat.format(value);
        } else {
            return name;
        }
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

    public Number(double value, String name) {
        this.value = value;
        this.name = name;
    }
}
