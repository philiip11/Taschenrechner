package calculator;

public class Brackets extends EquationElement {

    final static boolean OPENING = true;
    final static boolean CLOSING = false;

    boolean isOpening() {
        return type;
    }

    public boolean isClosing() {
        return !type;
    }

    private boolean type;

    Brackets(boolean type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type ? "(" : ")";
    }

    @Override
    public boolean isNumber() {
        return false;
    }
}
