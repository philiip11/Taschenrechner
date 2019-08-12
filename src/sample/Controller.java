package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    @FXML
    JFXTextField numbers;

    //TODO Refactor (Strg+F6) all buttons to lowercase
    @FXML
    JFXButton ENTER;
    @FXML
    JFXButton NUMPAD0;
    @FXML
    JFXButton NUMPAD1;
    @FXML
    JFXButton NUMPAD2;
    @FXML
    JFXButton NUMPAD3;
    @FXML
    JFXButton NUMPAD4;
    @FXML
    JFXButton NUMPAD5;
    @FXML
    JFXButton NUMPAD6;
    @FXML
    JFXButton NUMPAD7;
    @FXML
    JFXButton NUMPAD8;
    @FXML
    JFXButton NUMPAD9;
    @FXML
    JFXButton ADD;
    @FXML
    JFXButton SUBTRACT;
    @FXML
    JFXButton MULTIPLY;
    @FXML
    JFXButton DIVIDE;
    @FXML
    JFXButton ESCAPE;
    @FXML
    JFXButton DELETE;
    @FXML
    JFXButton BACK_SPACE;

    private Map<KeyCode, JFXButton> map = new HashMap<>();

    private ScriptEngine scriptEngine;
    private Calculator calculator;


    public void initialize() {
        //TODO Use Calculator Class
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        scriptEngine = scriptEngineManager.getEngineByName("js");

        map.put(KeyCode.ENTER, ENTER);
        map.put(KeyCode.NUMPAD0, NUMPAD0);
        map.put(KeyCode.NUMPAD1, NUMPAD1);
        map.put(KeyCode.NUMPAD2, NUMPAD2);
        map.put(KeyCode.NUMPAD3, NUMPAD3);
        map.put(KeyCode.NUMPAD4, NUMPAD4);
        map.put(KeyCode.NUMPAD5, NUMPAD5);
        map.put(KeyCode.NUMPAD6, NUMPAD6);
        map.put(KeyCode.NUMPAD7, NUMPAD7);
        map.put(KeyCode.NUMPAD8, NUMPAD8);
        map.put(KeyCode.NUMPAD9, NUMPAD9);
        map.put(KeyCode.ADD, ADD);
        map.put(KeyCode.SUBTRACT, SUBTRACT);
        map.put(KeyCode.MULTIPLY, MULTIPLY);
        map.put(KeyCode.DIVIDE, DIVIDE);
        map.put(KeyCode.ESCAPE, ESCAPE);
        map.put(KeyCode.DELETE, DELETE);
        map.put(KeyCode.BACK_SPACE, BACK_SPACE);
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

        //TODO Use Calculator Class
        try {
            String eval = numbers.getText();
            eval = eval.replace("÷", "/");
            eval = eval.replace("×", "*");
            if (eval.length() > 0) {
                numbers.setText(scriptEngine.eval(eval).toString());
            }
        } catch (ScriptException e) {
            numbers.setText("ERROR");
            e.printStackTrace();
        }
    }

    public void buttonClick(ActionEvent actionEvent) {
        String input = ((JFXButton) actionEvent.getSource()).getText();
        //TODO Use Calculator Class
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
            case "+":
            case "-":
            case "×":
            case "÷":
                numbers.setText(numbers.getText() + input);
                break;
            case "=":
                calc();
                break;
            case "C":
                numbers.setText("");
                break;
        }
    }

    public void onKeyTyped(KeyEvent keyEvent) {
        keyEvent.consume();
    }
}