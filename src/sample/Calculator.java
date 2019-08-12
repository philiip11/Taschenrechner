package sample;

import java.util.LinkedList;

public class Calculator {
    private LinkedList<EquationElement> equation = new LinkedList<>();
    private double result;

    void addElement(EquationElement element) {
        equation.add(element);
    }


    void clear() {
        equation.clear();
    }
    private void calc() {
        Number a = null;
        Number b;
        Number c;
        Operator o = null;

        //TODO do some more magic here
        for (EquationElement element : equation) {
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

    double getResult() {
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
