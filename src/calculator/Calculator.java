package calculator;

import java.util.ArrayList;

public class Calculator {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    private static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    private static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    private static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

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
        simplifyBrackets();

        int i = 0;
        while (i < equation.size()) {
            logHighlight(i, ANSI_BLUE);
            EquationElement element = equation.get(i);
            if (element instanceof Number) {
                if (a == null) {
                    a = (Number) element;
                    if (o != null) {
                        if (o.useOneArgument()) {
                            a = doCalculation(a, o, i);
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

    private void logHighlight(int h, String ansi_color) {
        logHighlight(h, h, ansi_color);
    }

    private void logHighlight(int h, int hstop, String ansi_color) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < equation.size(); i++) {
            if (i == h) {
                result.append(ansi_color);
            }
            result.append(equation.get(i));
            if (i == hstop) {
                result.append(ANSI_RESET);
            }
        }
        System.out.println(result);


    }

    private void simplifyBrackets() {
        EquationElement first = equation.get(0);
        EquationElement last = equation.get(equation.size() - 1);
        if (first instanceof Brackets && last instanceof Brackets) {
            if (((Brackets) first).isOpening() && ((Brackets) last).isClosing()) {
                int bracketCounter = 0;
                if (!unresolvedBrackets(bracketCounter)) {
                    System.out.println("Simplify Brackets:");
                    logHighlight(0, ANSI_RED);
                    logHighlight(equation.size() - 1, ANSI_RED);
                    equation.remove(first);
                    equation.remove(last);
                }
            }
        }

    }

    private boolean unresolvedBrackets(int bracketCounter) {
        for (int i = 1; i < equation.size() - 2; i++) {
            EquationElement element = equation.get(i);
            if (element instanceof Brackets) {
                Brackets bracket = (Brackets) element;
                if (bracket.isOpening()) {
                    bracketCounter++;
                } else {
                    bracketCounter--;
                }
                if (bracketCounter < 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private void addBrackets() {
        for (int priority = 3; priority > 1; priority--) {
            int i = 0;
            while (i < equation.size()) {
                EquationElement element = equation.get(i);
                if (element instanceof Operator) {
                    Operator o = (Operator) element;
                    if (o.getPriority() == priority) {
                        System.out.println("Add Brackets:");
                        addLeftBracket(i);
                        addRightBracket(i);
                        i++;  // Springe ein Element nach Rechts, da links eine Klammer hinzugefügt wurde.
                    }
                }
                i++;
            }
        }
    }


    private void addLeftBracket(int i) {
        boolean added = false;
        int bracketsCounter = 0;
        while (!added) {
            i--;
            EquationElement element = equation.get(i);
            if (element instanceof Brackets) {
                Brackets bracket = (Brackets) element;
                if (bracket.isOpening()) {
                    bracketsCounter++;
                } else {
                    bracketsCounter--;
                }
                if (bracketsCounter < 0) {
                    System.out.println("BracketsCounter is negative, something is horribly wrong! :'(");
                }
            } else if (element instanceof Number) {
                if (bracketsCounter == 0) {
                    equation.add(i, new Brackets(Brackets.OPENING));
                    logHighlight(i, ANSI_GREEN);
                    added = true;
                }
            }
        }
    }

    private void addRightBracket(int i) {
        boolean added = false;
        int bracketsCounter = 0;
        while (!added) {
            i++;
            EquationElement element = equation.get(i);
            if (element instanceof Brackets) {
                Brackets bracket = (Brackets) element;
                if (bracket.isOpening()) {
                    bracketsCounter++;
                } else {
                    bracketsCounter--;
                }
                if (bracketsCounter < 0) {
                    System.out.println("BracketsCounter is negative, something is horribly wrong! :'(");
                }
            } else if (element instanceof Number) {
                if (bracketsCounter == 0) {
                    equation.add(i + 1, new Brackets(Brackets.CLOSING));
                    logHighlight(i + 1, ANSI_GREEN);
                    added = true;
                }
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
            logHighlight(start, i + 1, ANSI_RED);
            equation.subList(start, i + 1).clear();
        }
        equation.add(start, new Number(subResult));
        logHighlight(start, ANSI_GREEN);
    }

    private Number doCalculation(Number a, Number b, Operator o, int i) {
        Number c;
        c = o.calc(a, b);
        System.out.println(ANSI_CYAN + a.toString() + " " + o.toString() + " " + b.toString() + " = " + c.toString() + ANSI_RESET);
        logHighlight(i - 2, i, ANSI_RED);
        equation.remove(i);            //b
        equation.remove(i - 1); //o
        equation.remove(i - 2); //a
        equation.add(i - 2, c);
        logHighlight(i - 2, ANSI_GREEN);
        a = c;
        return a;
    }

    private Number doCalculation(Number a, Operator o, int i) {
        Number c;
        Number b;
        c = o.calc(a);
        System.out.println(ANSI_CYAN + a.toString() + " " + o.toString() + " = " + c.toString() + ANSI_RESET);
        logHighlight(i - 1, i, ANSI_RED);
        equation.remove(i);
        equation.remove(i - 1);
        equation.add(i - 1, c);
        logHighlight(i - 1, ANSI_GREEN);
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