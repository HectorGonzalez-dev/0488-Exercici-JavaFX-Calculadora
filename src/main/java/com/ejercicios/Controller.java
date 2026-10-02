package com.ejercicios;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Controller {

    private final int DISPLAY_CHAR_LIMIT = 16;
    private boolean justCalculated = false;

    @FXML
    private TextField display;

    @FXML
    private Button btnClear;

    @FXML
    private Button btn0;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn4;

    @FXML
    private Button btn5;

    @FXML
    private Button btn6;

    @FXML
    private Button btn7;

    @FXML
    private Button btn8;

    @FXML
    private Button btn9;

    @FXML
    private Button btnDot;

    @FXML
    private Button btnEquals;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnSubtract;

    @FXML
    private Button btnMultiply;

    @FXML
    private Button btnDivide;

    @FXML
    private Text labelError;

    @FXML
    private void writeSelf(ActionEvent event) {
        Button button = (Button) event.getSource();
        String buttonText = button.getText();
        String displayText = display.getText();

        // Si se acaba de calcular algo borra el resultado y actualiza la flag
        if (justCalculated && (Character.isDigit(buttonText.charAt(0)) || buttonText.equals("."))) {
            display.clear();
            displayText = "";
            justCalculated = false;
            labelError.setText("");
        }

        if (displayText.length() == DISPLAY_CHAR_LIMIT - 1) {
            labelError.setText("[ERROR] Limite de caracteres alcanzados.");
        } else if (displayText.length() == DISPLAY_CHAR_LIMIT - 2 && !Character.isDigit(buttonText.charAt(0))) {
            labelError.setText("[ERROR] El ultimo caracter no puede ser un simbolo.");
        } else if (!displayText.isEmpty() && !Character.isDigit(buttonText.charAt(0)) && !Character.isDigit(displayText.charAt(displayText.length() - 1))) {
            labelError.setText("[ERROR] No pueden haber 2 simbolos juntos.");
        } else if (displayText.isEmpty() && !Character.isDigit(buttonText.charAt(0))) {
            labelError.setText("[ERROR] No puedes empezar por un simbolo.");
        } else if (buttonText.equals(".")) {
            // Consigue la posición del último operador.
            int lastOperator = Math.max(
                Math.max(displayText.lastIndexOf("+"), displayText.lastIndexOf("-")),
                Math.max(displayText.lastIndexOf("*"), displayText.lastIndexOf("/"))
            );

            String currentNumber = displayText.substring(lastOperator + 1);

            if (currentNumber.contains(".")) {
                labelError.setText("[ERROR] No puedes formar un numero invalido.");
            } else {
                display.appendText(buttonText);
            }
        } else {
            display.appendText(buttonText);
        }
    }

    @FXML
    private void clearText(ActionEvent event) {
        display.clear();
        labelError.setText("");
    }

    @FXML
    private void calculate(ActionEvent event) {
        String expression = display.getText();
        if (expression.isEmpty()) {
            return;
        }

        // No calcula si acaba en un simbolo
        char lastChar = expression.charAt(expression.length() - 1);
        if (!Character.isDigit(lastChar) && lastChar != ')') {
            labelError.setText("[ERROR] Expresión incompleta.");
            return;
        }

        try {
            double result = new ExpressionBuilder(expression).build().evaluate();

            if (Double.isNaN(result)) {
                display.setText("NaN");
                labelError.setText("[ERROR] Resultado no es un número.");
            } else if (Double.isInfinite(result)) {
                display.setText(result > 0 ? "∞" : "-∞");
                labelError.setText("[ERROR] Resultado infinito.");
            } else {
                // Formatea el resultado
                String resultText;
                if (result == (long) result) {
                    resultText = String.format("%d", (long) result);
                } else {
                    resultText = String.format("%.10f", result).replaceAll("0*$", "").replaceAll("\\.$", "");
                }

                // Usa notación cientifica si el numero es demasiado
                if (resultText.length() > DISPLAY_CHAR_LIMIT) {
                    resultText = String.format("%.6e", result);
                }

                display.setText(resultText);
                labelError.setText("");
            }
            justCalculated = true;

        } catch (ArithmeticException e) {
            display.setText("Error");
            labelError.setText("[ERROR] " + e.getMessage());
            justCalculated = true;
        } catch (Exception e) {
            display.setText("Error");
            labelError.setText("[ERROR] Expresión inválida.");
            justCalculated = true;
        }
    }
}
