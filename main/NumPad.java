package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumPad extends JFrame {
    private JTextField displayField;

    public NumPad() {
        setTitle("NumPad");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Display-Feld
        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setFont(new Font("Arial", Font.BOLD, 24));
        add(displayField, BorderLayout.NORTH);

        // Panel für die Tasten
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 3, 5, 5));

        // Buttons erstellen (0-9)
        for (int i = 1; i <= 9; i++) {
            addButton(buttonPanel, String.valueOf(i));
        }

        // Sondertasten (0, Clear)
        addButton(buttonPanel, "0");
        addButton(buttonPanel, "C");

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
                displayField.setText(""); // Eingabe zurücksetzen
            } else {
                displayField.setText(displayField.getText() + command);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NumPad numPad = new NumPad();
            numPad.setVisible(true);
        });
    }
}
