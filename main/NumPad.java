package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumPad extends JFrame {
    private JTextField displayField;
    private String enteredNumber = ""; // Speicherung der Eingabe
    SnackPanel sp = new SnackPanel();
    private double price;


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
        if (enteredNumber.length() > 0) { // Nur wenn genau 2 Ziffern eingegeben wurden
            int index = Integer.parseInt(enteredNumber.replaceAll("\\D", ""));


            // Überprüfung, ob x und y zwischen 0 und 4 liegen
            if (index <= 16) {
                index --;
                String name = sp.item[index].name;
                 price = sp.item[index].price;
                 sp.priceItem = price;
                 sp.buying = true;


                displayField.setText("Preis:" + price + "");


                // ChangeGUI-Fenster öffnen
                SwingUtilities.invokeLater(() -> {
                    ChangeGUI changeGui = new ChangeGUI();
                    changeGui.setVisible(true);
                });

                // Warte 1 Sekunde und setze Eingabe zurück
                Timer timer = new Timer(1000, event -> enteredNumber = "");
                timer.setRepeats(false); // Timer nur einmal ausführen
                timer.start();
            } else if (index == 9999) {
                AdminPanel ap = new AdminPanel();
            } else {
                displayField.setText("Ungültig!");
                enteredNumber = "";
            }
        } else {
            displayField.setText("Fehlende Ziffer!");
        }
    }

    // ChangeGUI-Klasse für das Rückgeld
    public class ChangeGUI extends JFrame {
        private JTextField eingeworfenField;
        private JLabel rueckGeldLabel;

        public ChangeGUI() {
            setTitle("Rückgeld Rechner");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(null);

            getContentPane().setBackground(Color.GRAY);

            JLabel preisLabel = new JLabel("Der Preis beträgt: " + price);
            preisLabel.setBounds(50, 20, 200, 20);
            preisLabel.setForeground(Color.DARK_GRAY);
            add(preisLabel);

            JLabel eingeworfenLabel = new JLabel("Eingeworfen:");
            eingeworfenLabel.setBounds(50, 50, 100, 20);
            eingeworfenLabel.setForeground(Color.DARK_GRAY);
            add(eingeworfenLabel);

            eingeworfenField = new JTextField();
            eingeworfenField.setBounds(150, 50, 80, 20);
            eingeworfenField.setBackground(Color.LIGHT_GRAY);
            eingeworfenField.setForeground(Color.DARK_GRAY);
            add(eingeworfenField);

            JButton berechnenButton = new JButton("Berechnen");
            berechnenButton.setBounds(50, 80, 180, 30);
            berechnenButton.setBackground(Color.LIGHT_GRAY);
            berechnenButton.setForeground(Color.DARK_GRAY);
            add(berechnenButton);

            rueckGeldLabel = new JLabel("");
            rueckGeldLabel.setBounds(50, 120, 200, 20);
            rueckGeldLabel.setForeground(Color.DARK_GRAY);
            add(rueckGeldLabel);

            berechnenButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        double eingeworfen = Double.parseDouble(eingeworfenField.getText());
                        double preis = Prices.priceCalculator();

                        if (preis == -1.0) {
                            rueckGeldLabel.setText("Ungültige Nummer!");
                            return;
                        }

                        double rueckGeld = eingeworfen - preis;

                        if (rueckGeld < 0) {
                            rueckGeldLabel.setText("Zu wenig Geld eingeworfen!");
                        } else if (rueckGeld == 0) {
                            rueckGeldLabel.setText("Kein Rückgeld nötig. Danke!");
                            sp.buying = false;
                        } else {
                            rueckGeldLabel.setText("Rückgeld: " + rueckGeld + " Fr.");
                            sp.buying = false;
                        }
                    } catch (NumberFormatException ex) {
                        rueckGeldLabel.setText("Ungültige Eingabe!");
                    }
                }

            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NumPad numPad = new NumPad();
            numPad.setVisible(true);
        });
    }
}