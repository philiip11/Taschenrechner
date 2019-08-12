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

public class Controller {
    @FXML
    JFXTextField numbers;
    ScriptEngineManager scriptEngineManager;
    ScriptEngine scriptEngine;

    public void initialize() {
        scriptEngineManager = new ScriptEngineManager();
        scriptEngine = scriptEngineManager.getEngineByName("js");
    }


    public void onKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            calc();
        }
    }

    public void calc() {
        try {
            String eval = numbers.getText();
            eval = eval.replace("÷", "/");
            eval = eval.replace("×", "*");

            numbers.setText(scriptEngine.eval(eval).toString());
        } catch (ScriptException e) {
            numbers.setText("ERROR");
            e.printStackTrace();
        }
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
}
