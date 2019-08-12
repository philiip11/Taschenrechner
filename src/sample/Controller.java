package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.Operators.Add;
import sample.Operators.Divide;
import sample.Operators.Multiply;
import sample.Operators.Subtract;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

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
    JFXButton plusminus;
    @FXML
    JFXButton percent;
    @FXML
    JFXButton sqrt;
    @FXML
    JFXButton square;
    @FXML
    JFXButton cube;
    @FXML
    JFXButton inverse;

    private Map<KeyCode, JFXButton> map = new HashMap<>();

    //private ScriptEngine scriptEngine;
    private Calculator calculator = new Calculator();
    private DecimalFormat decimalFormat = new DecimalFormat("#.########");

    public void initialize() {
        //TODO Use Calculator Class
        //ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        //scriptEngine = scriptEngineManager.getEngineByName("js");

        map.put(KeyCode.ENTER, enter);
        map.put(KeyCode.NUMPAD0, numpad0);
        map.put(KeyCode.NUMPAD1, numpad1);
        map.put(KeyCode.NUMPAD2, numpad2);
        map.put(KeyCode.NUMPAD3, numpad3);
        map.put(KeyCode.NUMPAD4, numpad4);
        map.put(KeyCode.NUMPAD5, numpad5);
        map.put(KeyCode.NUMPAD6, numpad6);
        map.put(KeyCode.NUMPAD7, numpad7);
        map.put(KeyCode.NUMPAD8, numpad8);
        map.put(KeyCode.NUMPAD9, numpad9);
        map.put(KeyCode.ADD, add);
        map.put(KeyCode.SUBTRACT, subtract);
        map.put(KeyCode.MULTIPLY, multiply);
        map.put(KeyCode.DIVIDE, divide);
        map.put(KeyCode.ESCAPE, escape);
        map.put(KeyCode.DELETE, delete);
        map.put(KeyCode.BACK_SPACE, back_space);
        map.put(KeyCode.DIGIT0, numpad0);
        map.put(KeyCode.DIGIT1, numpad1);
        map.put(KeyCode.DIGIT2, numpad2);
        map.put(KeyCode.DIGIT3, numpad3);
        map.put(KeyCode.DIGIT4, numpad4);
        map.put(KeyCode.DIGIT5, numpad5);
        map.put(KeyCode.DIGIT6, numpad6);
        map.put(KeyCode.DIGIT7, numpad7);
        map.put(KeyCode.DIGIT8, numpad8);
        map.put(KeyCode.DIGIT9, numpad9);
        map.put(KeyCode.DECIMAL, decimal);

        Platform.runLater(() -> numbers.requestFocus());
    }


    public void onKeyPressed(KeyEvent keyEvent) {
        JFXButton button = map.get(keyEvent.getCode());
        if (button != null) {
            button.disarm();
            button.arm();
            button.fire();
        }
        keyEvent.consume();

    }

    public void onKeyReleased(KeyEvent keyEvent) {
        JFXButton button = map.get(keyEvent.getCode());
        if (button != null) {
            button.disarm();
        }
    }

    private void calc() {

        numbers.setText(decimalFormat.format(calculator.getResult()));

        //TODO Use Calculator Class
        /*try {
            String eval = numbers.getText();
            eval = eval.replace("÷", "/");
            eval = eval.replace("×", "*");
            if (eval.length() > 0) {
                numbers.setText(scriptEngine.eval(eval).toString());
            }
        } catch (ScriptException e) {
            numbers.setText("ERROR");
            e.printStackTrace();
        }*/
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
            case "=":
                addNumber();
                updateEquation();
                calc();
                break;
            case "C":
                calculator.clear();
                updateEquation();

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

    private void addOperator(Operator op) {
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
}
