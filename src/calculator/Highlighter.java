package calculator;

import java.util.ArrayList;

public class Highlighter {
    static final String RESET = "\u001B[0m";
    static final String BLACK = "\u001B[30m";
    static final String RED = "\u001B[31m";
    static final String GREEN = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";
    static final String BLUE = "\u001B[34m";
    static final String PURPLE = "\u001B[35m";
    static final String CYAN = "\u001B[36m";
    static final String WHITE = "\u001B[37m";
    static final String BLACK_BACKGROUND = "\u001B[40m";
    static final String RED_BACKGROUND = "\u001B[41m";
    static final String GREEN_BACKGROUND = "\u001B[42m";
    static final String YELLOW_BACKGROUND = "\u001B[43m";
    static final String BLUE_BACKGROUND = "\u001B[44m";
    static final String PURPLE_BACKGROUND = "\u001B[45m";
    static final String CYAN_BACKGROUND = "\u001B[46m";
    static final String WHITE_BACKGROUND = "\u001B[47m";

    static void log(int h, String color, ArrayList<EquationElement> equation, int indent) {
        log(h, h, color, equation, indent);
    }

    static void log(int h, int hstop, String color, ArrayList<EquationElement> equation, int indent) {
        StringBuilder result = new StringBuilder();
        result.append(getIndent(indent));
        for (int i = 0; i < equation.size(); i++) {
            if (i == h) {
                result.append(color);
            }
            result.append(equation.get(i)).append(" ");
            if (i == hstop) {
                result.append(RESET);
            }
        }
        result.append(RESET);
        System.out.println(result);
    }


    static void log(int h, String color, int h2, ArrayList<EquationElement> equation, int indent) {
        StringBuilder result = new StringBuilder();
        result.append(getIndent(indent));
        for (int i = 0; i < equation.size(); i++) {
            if (i == h || i == h2) {
                result.append(color);
            }
            result.append(equation.get(i)).append(" ");
            if (i == h || i == h2) {
                result.append(RESET);
            }
        }
        result.append(RESET);
        System.out.println(result);
    }

    private static String getIndent(int indent) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            result.append(" ");
        }
        return result.toString();
    }
}
