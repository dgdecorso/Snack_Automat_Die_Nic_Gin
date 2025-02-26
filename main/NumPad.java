package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumPad extends JFrame {
    private JTextField displayField;
    private String enteredNumber = ""; // Speicherung der Eingabe
    private CashPanel cashPanel; // CashPanel-Referenz für Preisprüfung

    public NumPad(SnackPanel sp) { // Konstruktor nimmt SnackPanel als Parameter
        this.cashPanel = new CashPanel(sp); // CashPanel mit SnackPanel verknüpfen

        setTitle("NumPad");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Display-Feld
        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setFont(new Font("Arial", Font.BOLD, 24));
        displayField.setHorizontalAlignment(JTextField.CENTER);
        add(displayField, BorderLayout.NORTH);

        // Panel für die Tasten
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 3, 5, 5));

        // Buttons erstellen (1-9)
        for (int i = 1; i <= 9; i++) {
            addButton(buttonPanel, String.valueOf(i));
        }

        // Sondertasten (0, Clear, OK)
        addButton(buttonPanel, "C");
        addButton(buttonPanel, "0");
        addButton(buttonPanel, "OK");

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void addButton(JPanel panel, String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.addActionListener(new ButtonClickListener());
        panel.add(button);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("C")) {
                enteredNumber = "";
                displayField.setText("");
            } else if (command.equals("OK")) {
                processNumber();
            } else {
                if (enteredNumber.length() < 2) { // Maximal 2 Ziffern zulassen
                    enteredNumber += command;
                    displayField.setText(enteredNumber);
                }
            }
        }
    }

    private void processNumber() {
        if (enteredNumber.length() == 2) { // Nur wenn genau 2 Ziffern eingegeben wurden
            int x = Character.getNumericValue(enteredNumber.charAt(0));
            x -= 1;
            int y = Character.getNumericValue(enteredNumber.charAt(1));
            y -= 1;

            // Überprüfung, ob x und y zwischen 0 und 4 liegen
            if (x >= 0 && x <= 4 && y >= 0 && y <= 4) {
                double price = cashPanel.cashChecker(x, y); // Preis abrufen

                if (price >= 0) {
                    displayField.setText("Preis: " + price + "€");
                } else {
                    displayField.setText("Kein Produkt!");
                }

                // Warte 1,5 Sekunden und schließe das Fenster
                Timer timer = new Timer(1500, event -> setVisible(false));
                timer.setRepeats(false);
                timer.start();
            } else {
                displayField.setText("Ungültig!");
                enteredNumber = "";
            }
        } else {
            displayField.setText("Fehlende Ziffer!");
        }
    }
}
