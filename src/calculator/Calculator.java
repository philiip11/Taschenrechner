package calculator;

import java.util.ArrayList;

public class Calculator {

    private ArrayList<EquationElement> equation = new ArrayList<>();
    private double result;
    private int indent = 0;
    private boolean root;
    private int leftBracketPosition;

    Calculator(boolean root) {
        this.root = root;
    }

    void addElement(EquationElement element) {
        equation.add(element);
    }


    void clear() {
        equation.clear();
        indent = 0;
    }

    private void calc() {

        if (root) {
            addBrackets();          // Füge Klammern hinzu, damit Punkt vor Strichrechnung gilt.
        }                           // KlaPoPuS (Klammer vor Potenz vor Punkt vor Strich)
        while (simplifyBrackets()) ;// Entferne unnötige Klammern am Rand z.b. (1+2) => 1+2
        parseEquation();            // Gleichung ausrechnen
        parseResult();              // Ergebnis erkennen
        equation.clear();

    }


    private void parseEquation() {
        Number a = null;                // a (o) b = x z.b. 1 + 2 = 3
        Number b = null;
        Operator o = null;
        int i = 0;
        while (i < equation.size()) {
            //logHighlight(i, ANSI_BLUE);
            EquationElement element = equation.get(i);  // Gehe Elemente v.l.n.r. durch.
            if (element instanceof Number) {
                if (a == null) {
                    a = (Number) element;               // Weise element zu a zu, falls a leer
                    if (o != null) {
                        if (o.useOneArgument()) {       // Falls der Operator nur ein Argument braucht (z.b. Wurzel),
                            a = doCalculation(a, o, i); // dann führe diese Berechnung aus.
                            i--;
                        }
                    }
                } else if (b == null) {
                    b = (Number) element;               // Weise element zu b zu, falls a schon belegt und b leer
                    if (o != null) {
                        a = doCalculation(a, b, o, i);  // Führe Berechnung a (o) b = c aus und weise den Wert a zu
                        b = null;
                        i -= 2;                         // Verschiebe den Index nach links, da links Elemente entfernt wurden
                    } else {
                        System.out.println("Two Numbers without Operator, something is missing :(");
                    }
                } else {
                    System.out.println("Three Numbers in a row, that shouldn't happen :(");
                    break;
                }
            } else if (element instanceof Brackets) {
                i = handleBrackets(i);                  // Hier werden Klammern verarbeitet

            } else if (element instanceof Operator) {
                o = (Operator) element;                 // Merke den Operator für den nächsten Durchgang

            }
            i++;                                        // Schiebe den Index ein Element weiter
        }
    }

    private void parseResult() {
        if (equation.size() == 1) {
            result = ((Number) equation.get(0)).getValue(); // Das einzig verbleibende Element ist das Ergebnis
        } else {
            System.out.println("Multiple results!?");       // Wenn man Quatsch eingibt, kommt Quatsch heraus...
            System.out.println(this.toString());
        }
    }


    private boolean simplifyBrackets() {
        EquationElement first = equation.get(0);
        EquationElement last = equation.get(equation.size() - 1);
        if (first instanceof Brackets && last instanceof Brackets) {
            if (((Brackets) first).isOpening() && ((Brackets) last).isClosing()) {
                if (!unresolvedBrackets()) {
                    Highlighter.logHighlight(0, Highlighter.RED, equation.size() - 1, equation, indent);
                    equation.remove(first);             // Wenn erstes und letztes Element Klammern sind
                    equation.remove(last);              // und diese nicht benötigt werden, dann
                    indent += 2;                        // werden diese entfernt
                    return true;
                }
            }
        }
        return false;
    }

    private boolean unresolvedBrackets() {
        int bracketCounter = 0;
        for (int i = 1; i < equation.size() - 2; i++) {
            EquationElement element = equation.get(i);
            if (element instanceof Brackets) {
                Brackets bracket = (Brackets) element;
                if (bracket.isOpening()) {                  // Prüfe, ob Klammern am Ende nötig sind, z.b. sind bei
                    bracketCounter++;                       // (1+2)*(3+4) die Klammern nötig, bei
                } else {                                    // (1+2+3+4) nicht.
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
        for (int priority = 3; priority > 1; priority--) {      // Fange mit Priorität 3 (Exponenten) an
            int i = 0;                                          // Danach folgt Priorität 2 (Multiplizieren/Dividiedern)
            while (i < equation.size()) {
                EquationElement element = equation.get(i);
                if (element instanceof Operator) {
                    Operator o = (Operator) element;            // Füge so viele Klammern ein, so dass eindeutig ist,
                    if (o.getPriority() == priority) {          // in welcher Reihenfolge Operationen durchgeführt werden
                        //addLeftBracket(i);                      // sollen.
                        //addRightBracket(i);
                        addBracket(i, true);
                        addBracket(i, false);

                        i++;        // Springe ein Element nach Rechts, da links eine Klammer hinzugefügt wurde.
                    }
                }
                i++;
            }
        }
    }

    private void addBracket(int i, boolean isLeftBracket) {                // Finde die Stelle, an die die öffnende Klammer muss
        boolean added = false;
        int bracketsCounter = 0;
        EquationElement element;
        while (!added) {
            if (isLeftBracket) {              // Es wird von der Position des Operators nach links oder rechts gestartet,
                i--;                        // weil die öffnende Klammer links vom Operator stehen soll und die
            } else {                          // schließende Klammer rechts.
                i++;
            }
            if (i == equation.size()) {
                element = null;             // Falls die schließende Klammer ans Ende gesetzt werden soll,
            } else {                        // darf es keine NullPointerException geben.
                element = equation.get(i);
            }
            if (element instanceof Brackets) {
                bracketsCounter = updateBracketsCounter(isLeftBracket, bracketsCounter, element);
                if (bracketsCounter < 0) {
                    System.out.println("BracketsCounter is negative, something is horribly wrong! :'(");
                } else {
                    if (!isLeftBracket) {
                        i++;
                    }
                    added = addBracketAtPosition(i, bracketsCounter, isLeftBracket);
                    if (!isLeftBracket) {
                        i--;
                    }
                }
            } else if (element instanceof Number) {
                if (!isLeftBracket) {
                    i++;
                }
                added = addBracketAtPosition(i, bracketsCounter, isLeftBracket);
                if (!isLeftBracket) {
                    i--;
                }
            } else if (element == null) {
                int j = isLeftBracket ? 0 : i;                      // Klammer kommt ans Ende
                added = addBracketAtPosition(j, bracketsCounter, isLeftBracket);

            }
        }
    }

    private int updateBracketsCounter(boolean isLeftBracket, int bracketsCounter, EquationElement element) {
        Brackets bracket = (Brackets) element;
        if (isLeftBracket) {
            if (bracket.isClosing()) {              // Zähle die Klammern, in die er springt. Z.B.:
                bracketsCounter++;                  //          ( 2 * ( 3 + 4 ) + 1 ) * 5 = 75
            } else {                                //          1     2       1     0
                bracketsCounter--;
            }
        } else {
            if (bracket.isOpening()) {
                bracketsCounter++;
            } else {
                bracketsCounter--;
            }
        }
        return bracketsCounter;
    }
    private boolean addBracketAtPosition(int i, int bracketsCounter, boolean type) {
        if (bracketsCounter == 0) {             // Prüfe, ob alle Klammern aufgelöst sind
            equation.add(i, new Brackets(type));
            if (type == Brackets.OPENING) {
                leftBracketPosition = i;            // Merke Position, damit nur eine Zeile je Klammernpaar ausgegeben wird.
            } else {
                Highlighter.logHighlight(leftBracketPosition, Highlighter.GREEN, i, equation, indent);
            }
            return true;
        }
        return false;
    }


    private int handleBrackets(int i) {
        Calculator subCalculator = new Calculator(false);
        subCalculator.setIndent(getSubStringLength(0, i) + indent);
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
                replaceBracketsWithSubResult(subCalculator, start, i);
                i = start - 1;
            } else {
                subCalculator.addElement(subElement);
            }
        }
        return i;
    }

    private int getSubStringLength(int start, int end) {
        int result = 0;
        for (int i = start; i <= end; i++) {
            result += equation.get(i).toString().length() + 1;
        }
        return result;
    }

    private void replaceBracketsWithSubResult(Calculator subCalculator, int start, int end) {
        double subResult = subCalculator.getResult();
        Highlighter.logHighlight(start, end, Highlighter.RED, equation, indent);
        equation.subList(start, end + 1).clear();
        indent += end - start;
        equation.add(start, new Number(subResult));
        Highlighter.logHighlight(start, Highlighter.GREEN, equation, indent);
    }

    private Number doCalculation(Number a, Number b, Operator o, int i) {
        Number c;
        c = o.calc(a, b);
        System.out.println(getIndent() + Highlighter.CYAN + a.toString() + " " + o.toString() + " " + b.toString() + " = " + c.toString() + Highlighter.RESET);
        Highlighter.logHighlight(i - 2, i, Highlighter.RED, equation, indent);
        equation.remove(i);            //b
        equation.remove(i - 1); //o
        equation.remove(i - 2); //a
        equation.add(i - 2, c);
        Highlighter.logHighlight(i - 2, Highlighter.GREEN, equation, indent);
        a = c;
        return a;
    }

    private Number doCalculation(Number a, Operator o, int i) {
        Number c;
        Number b;
        c = o.calc(a);
        System.out.println(getIndent() + Highlighter.CYAN + a.toString() + " " + o.toString() + " = " + c.toString() + Highlighter.RESET);
        Highlighter.logHighlight(i - 1, i, Highlighter.RED, equation, indent);
        equation.remove(i);
        equation.remove(i - 1);
        equation.add(i - 1, c);
        Highlighter.logHighlight(i - 1, Highlighter.GREEN, equation, indent);
        a = c;
        return a;
    }

    private String getIndent() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            result.append(" ");
        }
        return result.toString();
    }

    public void setIndent(int indent) {
        this.indent = indent;
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
