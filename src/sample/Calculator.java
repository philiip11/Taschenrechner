package sample;

import java.util.ArrayList;

public class Calculator {
    private ArrayList<EquationElement> equation = new ArrayList<>();
    private double result;

    void addElement(EquationElement element) {
        equation.add(element);
    }


    void clear() {
        equation.clear();
    }

    private void calc() {
        Number a = null;
        Number b = null;
        Number c;
        Operator o = null;

        //TODO do some more magic here

        int i = 0;
        while (i < equation.size()) {
            System.out.println(this.toString());
            EquationElement element = equation.get(i);
            if (element.isNumber()) {
                if (a == null) {
                    a = (Number) element;
                    if (o != null) {
                        if (o.useOneArgument()) {
                            a = doOneSidedCalculation(a, o, i);
                            i--;
                        }
                    }
                } else if (b == null) {
                    b = (Number) element;
                    if (o != null) {
                        a = doCalculation(a, b, o, i);
                        b = null;
                        i -= 2;
                    } else {
                        System.out.println("Something went wrong. :(");
                    }
                } else {
                    System.out.println("Two Numbers without Operator, something is missing :(");
                    break;
                }
            } else {
                o = (Operator) element;
            }
            i++;
        }
        switch (equation.size()) {
            case 2:
                if (!(equation.get(1) instanceof Operator)) {
                    System.out.println("Multiple results!?");
                    break;
                }
            case 1:
                result = ((Number) equation.get(0)).getValue();
                break;
            default:
                System.out.println("Multiple results!?");
                System.out.println(this.toString());
                break;
        }
        equation.clear();

    }

    private Number doCalculation(Number a, Number b, Operator o, int i) {
        Number c;
        c = o.calc(a, b);
        System.out.println(a.toString() + " " + o.toString() + " " + b.toString() + " = " + c.toString());
        System.out.println(c.getValue());
        equation.remove(i);            //b
        equation.remove(i - 1); //o
        equation.remove(i - 2); //a
        equation.add(i - 2, c);
        a = c;
        return a;
    }

    private Number doOneSidedCalculation(Number a, Operator o, int i) {
        Number c;
        Number b;
        c = o.calc(a);
        System.out.println(a.toString() + " " + o.toString() + " = " + c.toString());
        equation.remove(i);
        equation.remove(i - 1);
        equation.add(i - 1, c);
        a = c;
        return a;
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
