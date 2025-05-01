import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI extends JFrame {

    private final JTextField display;
    private final Calculator calculator;
    private double firstNumber = 0;
    private String operator = "";

    public CalculatorGUI() {
        calculator = new Calculator();

        ImageIcon image = new ImageIcon("src/calc.png");


        // Display Area
        display = new JTextField();
        display.setEnabled(false);
        display.setFont(new Font("Arial", Font.BOLD, 30));


        // Panel for Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 5, 5));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        this.setTitle("Calculator");
        this.setSize(300, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setIconImage(image.getImage());

        this.add(display, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if ("0123456789".contains(command)) {
                display.setText(display.getText() + command);
            } else if ("+-*/".contains(command)) {
                try {
                    firstNumber = Double.parseDouble(display.getText());
                    operator = command;
                    display.setText("");
                } catch (NumberFormatException ex) {
                    display.setText("Error");
                }
            } else if ("=".equals(command)) {
                try {
                    double secondNumber = Double.parseDouble(display.getText());
                    double result = switch (operator) {
                        case "+" -> calculator.add(firstNumber, secondNumber);
                        case "-" -> calculator.subtract(firstNumber, secondNumber);
                        case "*" -> calculator.multiply(firstNumber, secondNumber);
                        case "/" -> calculator.divide(firstNumber, secondNumber);
                        default -> 0;
                    };
                    display.setText(String.valueOf(result));
                } catch (Exception ex) {
                    display.setText("Error");
                }
            } else if ("C".equals(command)) {
                display.setText("");
                operator = "";
                firstNumber = 0;
            }
        }
    }
}