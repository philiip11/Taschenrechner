package calculator;

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
        addBrackets();

        int i = 0;
        while (i < equation.size()) {
            System.out.println(this.toString());
            EquationElement element = equation.get(i);
            if (element instanceof Number) {
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
            } else if (element instanceof Brackets) {
                i = handleBrackets(i);

            } else if (element instanceof Operator) {
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

    private void addBrackets() {
        int i = 0;
        for (int p = 3; p > 1; p--) {
            while (i < equation.size()) {
                System.out.println(this.toString());
                EquationElement element = equation.get(i);
                if (element instanceof Operator) {
                    Operator o = (Operator) element;
                    int priority = o.getPriority();
                }
                i++;
            }
        }
    }

    private int handleBrackets(int i) {
        Calculator subCalculator = new Calculator();
        int start = i;
        int bracketsCounter = 1;
        boolean inLoop = true;
        EquationElement subElement;
        while (inLoop) {
            i++;
            subElement = equation.get(i);
            if (subElement instanceof Brackets) {
                Brackets brackets = (Brackets) subElement;
                if (brackets.isOpening()) {
                    bracketsCounter++;
                } else {
                    bracketsCounter--;
                }
            }
            if (bracketsCounter == 0) {
                inLoop = false;
                replaceBracketsWithSubResult(i, subCalculator, start);
                i = start - 1;
            } else {
                subCalculator.addElement(subElement);
            }
        }
        return i;
    }

    private void replaceBracketsWithSubResult(int i, Calculator subCalculator, int start) {
        double subResult = subCalculator.getResult();
        if (i >= start) {
            equation.subList(start, i + 1).clear();
        }
        equation.add(start, new Number(subResult));
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
