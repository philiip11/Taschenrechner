package calculator;

import calculator.Operators.*;
import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static javafx.scene.input.KeyCombination.*;

public class Controller {
    @FXML
    Label equation;
    @FXML
    JFXTextField numbers;
    //DONE Refactor (Shift+F6) all buttons to lowercase
    @FXML
    JFXButton enter;
    @FXML
    JFXButton numpad0;
    @FXML
    JFXButton numpad1;
    @FXML
    JFXButton numpad2;
    @FXML
    JFXButton numpad3;
    @FXML
    JFXButton numpad4;
    @FXML
    JFXButton numpad5;
    @FXML
    JFXButton numpad6;
    @FXML
    JFXButton numpad7;
    @FXML
    JFXButton numpad8;
    @FXML
    JFXButton numpad9;
    @FXML
    JFXButton add;
    @FXML
    JFXButton subtract;
    @FXML
    JFXButton multiply;
    @FXML
    JFXButton divide;
    @FXML
    JFXButton escape;
    @FXML
    JFXButton delete;
    @FXML
    JFXButton back_space;
    @FXML
    JFXButton decimal;
    @FXML
    JFXButton bracketClose;
    @FXML
    JFXButton percent;
    @FXML
    JFXButton sqrt;
    @FXML
    JFXButton power;
    @FXML
    JFXButton bracketOpen;
    @FXML
    JFXButton plusMinus;
    @FXML
    JFXBadge bracketCounterBadge;

    private Map<KeyCode, JFXButton> map = new HashMap<>();
    private Map<KeyCode, JFXButton> shiftComboMap = new HashMap<>();
    private Map<KeyCode, JFXButton> strgAltComboMap = new HashMap<>();


    //private ScriptEngine scriptEngine;
    private Calculator calculator = new Calculator(true);
    private DecimalFormat decimalFormat = new DecimalFormat("#.############");
    private boolean clearOnNextInput = false;

    private int bracketsCounter = 0;

    public void initialize() {
        //TODO Use Calculator Class
        //ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        //scriptEngine = scriptEngineManager.getEngineByName("js");

        map.put(KeyCode.ENTER, enter);
        map.put(KeyCode.NUMPAD0, numpad0);      //numpad
        map.put(KeyCode.NUMPAD1, numpad1);      //numpad
        map.put(KeyCode.NUMPAD2, numpad2);      //numpad
        map.put(KeyCode.NUMPAD3, numpad3);      //numpad
        map.put(KeyCode.NUMPAD4, numpad4);      //numpad
        map.put(KeyCode.NUMPAD5, numpad5);      //numpad
        map.put(KeyCode.NUMPAD6, numpad6);      //numpad
        map.put(KeyCode.NUMPAD7, numpad7);      //numpad
        map.put(KeyCode.NUMPAD8, numpad8);      //numpad
        map.put(KeyCode.NUMPAD9, numpad9);      //numpad
        map.put(KeyCode.ADD, add);              //numpad +
        map.put(KeyCode.PLUS, add);             //keyboard +
        map.put(KeyCode.SUBTRACT, subtract);    //numpad -
        map.put(KeyCode.MINUS, subtract);       //keyboard -
        map.put(KeyCode.MULTIPLY, multiply);    //numpad *
        map.put(KeyCode.DIVIDE, divide);        //numpad /
        map.put(KeyCode.ESCAPE, escape);
        map.put(KeyCode.DELETE, delete);
        map.put(KeyCode.BACK_SPACE, back_space);
        map.put(KeyCode.DIGIT0, numpad0);       //keyboard
        map.put(KeyCode.DIGIT1, numpad1);       //keyboard
        map.put(KeyCode.DIGIT2, numpad2);       //keyboard
        map.put(KeyCode.DIGIT3, numpad3);       //keyboard
        map.put(KeyCode.DIGIT4, numpad4);       //keyboard
        map.put(KeyCode.DIGIT5, numpad5);       //keyboard
        map.put(KeyCode.DIGIT6, numpad6);       //keyboard
        map.put(KeyCode.DIGIT7, numpad7);       //keyboard
        map.put(KeyCode.DIGIT8, numpad8);       //keyboard
        map.put(KeyCode.DIGIT9, numpad9);       //keyboard
        map.put(KeyCode.DECIMAL, decimal);      //numpad ,
        map.put(KeyCode.COMMA, decimal);        //keyboard ,
        map.put(KeyCode.F9, plusMinus);        //keyboard ,
        map.put(KeyCode.DEAD_CIRCUMFLEX, power);

        //TODO ^-Taste für Potenzen
        shiftComboMap.put(KeyCode.DIGIT5, percent);
        shiftComboMap.put(KeyCode.DIGIT7, divide);
        shiftComboMap.put(KeyCode.DIGIT8, bracketOpen);
        shiftComboMap.put(KeyCode.DIGIT9, bracketClose);
        shiftComboMap.put(KeyCode.SUBTRACT, plusMinus);

        strgAltComboMap.put(KeyCode.Q, sqrt);



        Platform.runLater(() -> numbers.requestFocus());

        bracketCounterBadge.setEnabled(false);
    }



    public void onKeyPressed(KeyEvent keyEvent) {
        //JFXButton button = map.get(keyEvent.getCode());
        JFXButton button = findKey(keyEvent);

        if (button != null) {
            button.disarm();
            button.arm();
            button.fire();
        }
        keyEvent.consume();

    }

    public void onKeyReleased(KeyEvent keyEvent) {
        //JFXButton button = map.get(keyEvent.getCode());
        JFXButton button = findKey(keyEvent);
        if (button != null) {
            button.disarm();
        }
    }

    private void calc() {

        numbers.setText(decimalFormat.format(calculator.getResult()));
        calculator.clear();
    }

    public void buttonClick(ActionEvent actionEvent) {
        String input = ((JFXButton) actionEvent.getSource()).getText();
        switch (input) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case ",":
                clearIfNecessary();
                numbers.setText(numbers.getText() + input);
                break;

            case "+":
                addOperator(new Add());
                break;
            case "-":
                addOperator(new Subtract());
                break;
            case "×":
                addOperator(new Multiply());
                break;
            case "÷":
                addOperator(new Divide());
                break;
            case "√":
                addOperator(new SquareRoot());
                break;
            case "(":
                addBracket(Brackets.OPENING);
                break;
            case ")":
                addBracket(Brackets.CLOSING);
                break;
            case "^":
                addOperator(new Power());
                break;
            case "%":
                addOperator(new Percentage());
                break;
            case "±":
                plusMinusKey();
                break;

            case "=":
                addNumber();
                addMissingBrackets();
                updateEquation();
                calc();
                resetBracketsCounter();
                clearOnNextInput = true;
                break;
            case "C":
                calculator.clear();
                updateEquation();
                resetBracketsCounter();
            case "CE":
                numbers.setText("");
                break;
            case "⌫":
                if (numbers.getLength() > 0) {
                    numbers.setText(numbers.getText(0, numbers.getLength() - 1));
                }
                break;
        }
    }

    private void addBracket(boolean type) {
        clearIfNecessary();
        if (updateBracketsCounter(type ? 1 : -1)) {
            addOperator(new Brackets(type));
        }
    }

    private void addMissingBrackets() {
        while (bracketsCounter > 0) {
            addBracket(Brackets.CLOSING);
        }
    }

    private void resetBracketsCounter() {
        updateBracketsCounter(-bracketsCounter);
    }

    private boolean updateBracketsCounter(int i) {
        bracketsCounter += i;
        if (bracketsCounter < 0) {
            bracketsCounter = 0;
            return false;
        }
        if (bracketsCounter == 0) {
            bracketCounterBadge.setEnabled(false);
        } else {
            bracketCounterBadge.setEnabled(true);
        }
        bracketCounterBadge.setText(String.valueOf(bracketsCounter));
        return true;

    }

    private void plusMinusKey() {
        try {
            numbers.setText(String.valueOf(Integer.parseInt(numbers.getText()) * (-1)));
        } catch (NumberFormatException ignored) {
        }
    }

    private void clearIfNecessary() {
        if (clearOnNextInput) {
            clearOnNextInput = false;
            numbers.setText("");
            updateEquation();
            resetBracketsCounter();
        }
    }

    private void addOperator(EquationElement op) {
        addNumber();
        calculator.addElement(op);
        updateEquation();
    }

    private void addNumber() {
        if (!numbers.getText().isEmpty()) {
            Number n = new Number(Double.parseDouble(numbers.getText().replace(",", ".")));
            numbers.setText("");
            calculator.addElement(n);
        }
    }

    private void addNumber(double d) {
        Number n = new Number(d);
        calculator.addElement(n);
        updateEquation();
    }

    private void updateEquation() {
        equation.setText(calculator.toString());
    }

    public void onKeyTyped(KeyEvent keyEvent) {
        keyEvent.consume();
    }

    private JFXButton findKey(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        if (shiftComboMap.get(keyCode) != null) {
            KeyCodeCombination combination = new KeyCodeCombination(keyCode, SHIFT_DOWN);  // SHIFT anstelle von CTRL :)
            if (combination.match(keyEvent)) {
                return shiftComboMap.get(keyCode);
            }
        }
        if (strgAltComboMap.get(keyCode) != null) {
            KeyCodeCombination combination = new KeyCodeCombination(keyCode, CONTROL_DOWN, ALT_DOWN);  // ALT GR?
            if (combination.match(keyEvent)) {
                return strgAltComboMap.get(keyCode);
            }
        }
        //if no combinations found use single button mapping
        return map.get(keyCode);
    }


}
