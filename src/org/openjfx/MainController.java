package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.openjfx.calc.Model;

import java.util.IllegalFormatException;

public class MainController {
    private Model jshell = new Model();
    private String memory ="";
    private String operator = "";
    private boolean clean = false;

    private void alert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("ERROR");
        alert.setContentText("Press OK to continue");
        alert.setHeaderText(text);
        alert.showAndWait();
    }

    @FXML
    private Button button_1;
    @FXML
    private Button button_2;
    @FXML
    private Button button_3;
    @FXML
    private Button button_4;
    @FXML
    private Button button_5;
    @FXML
    private Button button_6;
    @FXML
    private Button button_7;
    @FXML
    private Button button_8;
    @FXML
    private Button button_9;
    @FXML
    private Button button_0;
    @FXML
    private Button button_AC;
    @FXML
    private Button button_CE;
    @FXML
    private Button button_modulo;
    @FXML
    private Button button_divide;
    @FXML
    private Button button_multiply;
    @FXML
    private Button button_minus;
    @FXML
    private Button button_plus;
    @FXML
    private Button button_coma;
    @FXML
    private Button button_power;
    @FXML
    private Button button_factorial;
    @FXML
    private Button button_equals;
    @FXML
    private Button button_sign;
    @FXML
    private TextField textField;

    private void clear() {
        textField.setText("");
        memory = "";
        operator = "";
        clean = false;
    }

    public void initialize() {
        button_AC.setOnAction(e -> {
                    clear();
                }
        );

        Button[] operatorButtons = {button_divide, button_multiply, button_plus, button_minus, button_modulo,
                button_power, button_factorial};

        Button[] numericButtons = {button_0, button_1, button_2, button_3, button_4, button_5, button_6, button_7,
                button_8, button_9};

        for (Button button : numericButtons) {
            button.setOnAction(e -> {
                if (clean) clear();
                textField.setText(textField.getText() + button.getText());
            });
        }

        for (Button newOperator : operatorButtons) {
            newOperator.setOnAction(e -> {
                if (!clean) calculate();

                clean = false;
                memory = textField.getText();
                operator = newOperator.getText();
                textField.setText("");
            });
        }

        button_AC.setOnAction(e -> {
            clear();
        });

        button_CE.setOnAction(e -> {
            if (!textField.getText().equals("")) {
                if (textField.getText().length() > 0) {
                    textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
                    if (textField.getText().contains(".")) {
                        while ((textField.getText().charAt(textField.getText().length() - 1) == '0'))
                            textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
                        if ((textField.getText().charAt(textField.getText().length() - 1) == '.'))
                            textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
                    } else if
                    (textField.getText().length() == 1 && textField.getText().contains("-")) {
                        textField.setText("");
                    }
                } else {
                    textField.setText("");
                }
            } else {
                alert("There is nothing more to delete.");
            }

        });

        button_sign.setOnAction(e -> {
            if (textField.getText().isEmpty());
            else if (Double.parseDouble(textField.getText()) > 0) textField.setText("-" + textField.getText());
            else if (Double.parseDouble(textField.getText()) < 0) textField.setText(textField.getText().substring(1));
        });


        button_coma.setOnAction(e -> {
            if (clean) clear();
            if (!textField.getText().contains(".")) {
                if (!textField.getText().isEmpty())
                    textField.setText(textField.getText() + ".");
            } else alert("You can't use comma twice!");
        });

        button_equals.setOnAction(e -> {
            calculate();

            textField.setText(memory);
            clean = true;
        });
    }

    private void calculate() {
        if (!operator.equals("")) {
            if (operator.equals("!"))
                try {
                    memory = jshell.calculate(memory);
                } catch (NumberFormatException a) {
                    alert(a.getMessage());
                    clear();
                }
            else {
                try {
                    memory = jshell.calculate(memory, operator, textField.getText());
                } catch (ArithmeticException a) {
                    alert(a.getMessage());
                    clear();
                } catch (IllegalFormatException a) {
                    alert("You can perform only one operation at a time!");
                    clear();
                }
            }
        }else if (textField.getText().length() == 0)
            alert("You cannot calculate without a second number!");
        else
            memory = textField.getText();
    }


}
