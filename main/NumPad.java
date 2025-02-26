package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumPad extends JFrame {
    private JTextField displayField;
    private String enteredNumber = ""; // Speicherung der Eingabe

    public NumPad() {
        setTitle("NumPad");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Pixel-Font laden (muss in den Ressourcen vorhanden sein)
        Font pixelFont = new Font("Monospaced", Font.BOLD, 24);

        // Display-Feld
        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setFont(pixelFont);
        displayField.setHorizontalAlignment(JTextField.CENTER);
        displayField.setBackground(Color.DARK_GRAY);
        displayField.setForeground(Color.WHITE);
        add(displayField, BorderLayout.NORTH);

        // Panel für die Tasten
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 3, 5, 5));
        buttonPanel.setBackground(Color.GRAY);

        // Buttons erstellen (1-9)
        for (int i = 1; i <= 9; i++) {
            addButton(buttonPanel, String.valueOf(i), pixelFont);
        }

        // Sondertasten (C, 0, OK)
        addButton(buttonPanel, "C", pixelFont);
        addButton(buttonPanel, "0", pixelFont);
        addButton(buttonPanel, "OK", pixelFont);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void addButton(JPanel panel, String text, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.GRAY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
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
            int y = Character.getNumericValue(enteredNumber.charAt(0));
            int x = Character.getNumericValue(enteredNumber.charAt(1));

            // Überprüfung, ob x und y zwischen 0 und 4 liegen
            if (x >= 0 && x <= 4 && y >= 0 && y <= 4) {
                System.out.println("Eingabe gespeichert: X=" + x + ", Y=" + y);

                displayField.setText("Gespeichert: " + enteredNumber);
                // Warte 1 Sekunde und schließe das Fenster
                Timer timer = new Timer(1000, event -> setVisible(false));
                timer.setRepeats(false); // Timer nur einmal ausführen
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
