package calculator;

import calculator.Operators.*;
import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static javafx.scene.input.KeyCombination.*;

public class Controller {
    @FXML
    Label equation;
    @FXML
    JFXTextField numbers;
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
    JFXButton modulo;
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
    JFXButton pi;
    @FXML
    JFXButton euler;

    @FXML
    JFXBadge bracketCounterBadge;
    @FXML
    JFXListView<Label> history;


    @FXML
    GridPane root;

    private Map<KeyCode, JFXButton> map = new HashMap<>();
    private Map<KeyCode, JFXButton> shiftComboMap = new HashMap<>();
    private Map<KeyCode, JFXButton> strgAltComboMap = new HashMap<>();
    private Map<KeyCode, Runnable> strgComboMap = new HashMap<>();

    private Clipboard clipboard = Clipboard.getSystemClipboard();
    private final String placeholder = ".";
    private RotateTransition flipper;


    //private ScriptEngine scriptEngine;
    private Calculator calculator = new Calculator(true);
    private DecimalFormat decimalFormat = new DecimalFormat("#.############");
    private boolean clearOnNextInput = false;

    private int bracketsCounter = 0;

    public void initialize() {
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
        map.put(KeyCode.DEAD_CIRCUMFLEX, power); // ^
        map.put(KeyCode.P, pi); // ^
        map.put(KeyCode.E, euler); // ^



        strgComboMap.put(KeyCode.C, this::copy);
        strgComboMap.put(KeyCode.V, this::paste);

        shiftComboMap.put(KeyCode.DIGIT5, percent);
        shiftComboMap.put(KeyCode.DIGIT7, divide);
        shiftComboMap.put(KeyCode.DIGIT8, bracketOpen);
        shiftComboMap.put(KeyCode.DIGIT9, bracketClose);
        shiftComboMap.put(KeyCode.SUBTRACT, plusMinus);
        shiftComboMap.put(KeyCode.DIGIT0, enter);
        shiftComboMap.put(KeyCode.PLUS, multiply);
        shiftComboMap.put(KeyCode.M, modulo);


        strgAltComboMap.put(KeyCode.Q, sqrt);


        Platform.runLater(() -> numbers.requestFocus());

        bracketCounterBadge.setEnabled(false);

        history.depthProperty().set(1);
        history.setExpanded(true);

        flipper = new RotateTransition(Duration.millis(500), enter);
        flipper.setAxis(Rotate.X_AXIS);
        flipper.setFromAngle(0);
        flipper.setToAngle(360);
        flipper.setInterpolator(Interpolator.EASE_BOTH);
        flipper.setCycleCount(1);

    }


    public void onKeyPressed(KeyEvent keyEvent) {
        //JFXButton button = map.get(keyEvent.getCode());
        JFXButton button = findKey(keyEvent, true);

        if (button != null) {
            button.disarm();
            button.arm();
            button.fire();
            if (keyEvent.getCode().toString().contains("DEAD")) {       // Tote Tasten wie z.B. ^ lösen kein onKeyReleased aus
                new Thread(() -> {                                      // deshalb wird hier nach 300ms ein loslassen simuliert
                    try {
                        Thread.sleep(300);
                        Platform.runLater(button::disarm);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
        keyEvent.consume();

    }

    public void onKeyReleased(KeyEvent keyEvent) {
        //JFXButton button = map.get(keyEvent.getCode());
        //        System.out.println(keyEvent.getCharacter());
        //        System.out.println(keyEvent.getText());
        //        System.out.println(keyEvent.getCode());
        JFXButton button = findKey(keyEvent, false);
        if (button != null) {
            button.disarm();
        }
    }

    void calc() {

        numbers.setText(decimalFormat.format(calculator.getResult()));
        formatNumber();
        addToHistory();
        calculator.clear();

    }

    private void addToHistory() {
        String text = equation.getText() + " =\n" + numbers.getText();
        history.getItems().add(0, new Label(text));
        history.setExpanded(true);
    }


    public void onHistoryClick() {
        Label l = history.getSelectionModel().getSelectedItem();
        String eq = l.getText().split("\n")[0].replace('=', ' ').trim();
        String n = l.getText().split("\n")[1];
        calculator.clear();
        parseText(eq);
        numbers.setText(n);
        formatNumber();
        updateEquation();

    }

    private void copy() {
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(numbers.getText());
        clipboard.setContent(clipboardContent);
    }

    private void paste() {
        parseText(clipboard.getString());
    }

    void parseText(String t) {
        for (int i = 0; i < t.length(); i++) {
            handleKey(t.substring(i, i + 1));
        }
        addNumber();
        updateEquation();
    }

    public void buttonClick(ActionEvent actionEvent) {
        String input = ((JFXButton) actionEvent.getSource()).getText();
        handleKey(input);
    }

    private void handleKey(String input) {
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
                handleNumberKey(input);
                break;

            case "π":
                addNumber(Math.PI, input);
                break;

            case "e":
                addNumber(Math.E, input);
                break;

            case "+":
                addOperator(new Add());
                break;
            case "-":
                addOperator(new Subtract());
                break;
            case "×":
            case "*":
                addOperator(new Multiply());
                break;
            case "/":
            case "÷":
                addOperator(new Divide());
                break;
            case"Mod":
                addOperator(new Modulo());
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
                flipper.play();
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
                    formatNumber();
                }
                break;
        }
    }

    private void handleNumberKey(String input) {
        clearIfNecessary();
        numbers.setText(numbers.getText() + input);
        formatNumber();

    }

    private void formatNumber() {
        String text = numbers.getText();
        text = text.replace(placeholder, "");
        int start = text.length() - 3;
        if (text.contains(",")) {
            start = text.indexOf(",") - 3;
        }
        for (int i = start; i > 0; i -= 3) {
            text = text.substring(0, i) + placeholder + text.substring(i);
        }
        numbers.setText(text);
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
            numbers.setText(String.valueOf(Integer.parseInt(numbers.getText().replace(placeholder, "")) * (-1)));
            formatNumber();
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
            String text = numbers.getText().replace(placeholder, "").replace(",", ".");
            Number n = new Number(Double.parseDouble(text));
            numbers.setText("");
            calculator.addElement(n);
        }
    }

    private void addNumber(double d) {
        clearIfNecessary();
        if (!numbers.getText().equals("")) {
            addOperator(new Multiply());
        }
        Number n = new Number(d);
        calculator.addElement(n);
        updateEquation();
    }

    private void addNumber(double d, String name) {
        clearIfNecessary();
        if (!numbers.getText().equals("")) {
            addOperator(new Multiply());
        }
        Number n = new Number(d, name);
        calculator.addElement(n);
        updateEquation();
    }


    private void updateEquation() {
        equation.setText(calculator.toString());
    }

    public void onKeyTyped(KeyEvent keyEvent) {
        keyEvent.consume();
    }

    private JFXButton findKey(KeyEvent keyEvent, boolean pressed) {
        KeyCode keyCode = keyEvent.getCode();
        if (shiftComboMap.get(keyCode) != null) {
            KeyCodeCombination combination = new KeyCodeCombination(keyCode, SHIFT_DOWN); // SHIFT anstelle von CTRL :)
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
        if (strgComboMap.get(keyCode) != null) {
            KeyCodeCombination combination = new KeyCodeCombination(keyCode, CONTROL_DOWN); // STRG
            if (combination.match(keyEvent) && pressed) {
                strgComboMap.get(keyCode).run();
            }
        }
        //if no combinations found use single button mapping
        return map.get(keyCode);
    }


}
