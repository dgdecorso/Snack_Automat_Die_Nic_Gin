
package main;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JFrame {

    public AdminPanel() {
        setTitle("Admin Panel");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Pixel-Font laden
        Font pixelFont = new Font("Monospaced", Font.BOLD, 24);

        // Display-Feld
        JTextField displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setFont(pixelFont);
        displayField.setHorizontalAlignment(JTextField.CENTER);
        displayField.setBackground(Color.DARK_GRAY);
        displayField.setForeground(Color.GRAY);
        add(displayField, BorderLayout.NORTH);

        // Panel für die Tasten
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 5, 5));
        buttonPanel.setBackground(Color.GRAY);

        // Buttons erstellen (1-9)
        for (int i = 1; i <= 3; i++) {
            addButton(buttonPanel, String.valueOf(i), pixelFont, displayField);
        }

        add(buttonPanel, BorderLayout.CENTER);

        // Fenster sichtbar machen
        setVisible(true);
    }

    private void addButton(JPanel panel, String text, Font font, JTextField displayField) {
        JButton button = new JButton(text);
        button.setFont(font);

        if (text.equals("1")) {
            button.addActionListener(e -> {
                Admin.RefillAll();
                displayField.setText("Snacks aufgefüllt!");
            });
        }
        else
        {
            button.addActionListener(e -> displayField.setText(displayField.getText() + text));
        }

        panel.add(button);
    }



}