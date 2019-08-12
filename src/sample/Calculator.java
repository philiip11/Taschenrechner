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


    public void clear() {
        equation.clear();
    }
    private void calc() {
        Number a = null;
        Number b = null;
        Number c = null;
        Operator o = null;
        //TODO do some magic here
        for (int i = 0; i < equation.size(); i++) {
            EquationElement element = equation.get(i);
            if (element.isNumber()) {
                if (a == null) {
                    a = (Number) element;
                    if (o != null) {
                        if (o.useOneArgument()) {
                            c = o.calc(a);
                            System.out.println(a.toString() + o.toString() + "=" + c.toString());
                            System.out.println(c.getValue());
                        }
                    }
                } else {
                    b = (Number) element;
                    if (o != null) {
                        c = o.calc(a, b);
                        System.out.println(a.toString() + o.toString() + b.toString() + "=" + c.toString());
                        System.out.println(c.getValue());
                    } else {
                        System.out.println("Something went wrong. :(");
                    }
                }
            } else {
                o = (Operator) element;

            }
        }

        result = 0;
    }

    public double getResult() {
        calc();
        return result;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (EquationElement element : equation) {
            result.append(element.toString()).append(" ");
        }
        return result.toString().trim();
    }
}
