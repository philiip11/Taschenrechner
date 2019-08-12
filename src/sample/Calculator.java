package sample;

import java.util.ArrayList;

public class Calculator {
    private ArrayList<EquationElement> equation;
    private double result;

    public Calculator() {
        equation = new ArrayList<>();
    }

    public void addElement(EquationElement element) {
        equation.add(element);
    }


    private void calc() {
        for (EquationElement element : equation) {
            //TODO do some magic here
        }

        result = 0;
    }

    public double getResult() {
        calc();
        return result;
    }

}
