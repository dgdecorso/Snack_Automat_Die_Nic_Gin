package main;

import java.text.DecimalFormat;
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
        if (!enteredNumber.isEmpty()) { // Stelle sicher, dass etwas eingegeben wurde
            try {
                int index = Integer.parseInt(enteredNumber); // Zahl umwandeln

                if (index >= 1 && index <= 16) { // Prüfe, ob die Nummer im gültigen Bereich liegt
                    index--; // Array beginnt bei 0

                    // ❗ PRÜFUNG: Ist das Array `sp.item` überhaupt gefüllt?
                    if (sp.item == null || sp.item.length < 16 || sp.item[index] == null) {
                        displayField.setText("FEHLER: Snacks nicht geladen!");
                        return;
                    }

                    // **Snack-Daten setzen**
                    String name = sp.item[index].name;
                    price = sp.item[index].price;
                    sp.priceItem = price;
                    sp.buying = true;

                    displayField.setText("Preis: " + price + " CHF");

                    // Öffne ChangeGUI nur, wenn Preis > 0 ist
                    if (price > 0) {
                        SwingUtilities.invokeLater(() -> {
                            ChangeGUI changeGui = new ChangeGUI();
                            changeGui.setVisible(true);
                        });
                    } else {
                        displayField.setText("FEHLER: Preis ungültig!");
                    }

                    // Timer, um Eingabe nach 1 Sekunde zu löschen
                    Timer timer = new Timer(1000, event -> enteredNumber = "");
                    timer.setRepeats(false);
                    timer.start();

                } else if (index == 9999) {
                    AdminPanel ap = new AdminPanel();
                } else {
                    displayField.setText("❌ Ungültige Nummer!");
                    enteredNumber = "";
                }
            } catch (NumberFormatException e) {
                displayField.setText("❌ Fehlerhafte Eingabe!");
                enteredNumber = "";
            }
        } else {
            displayField.setText("❌ Fehlende Ziffer!");
        }
    }



    // ChangeGUI-Klasse für das Rückgeld

    public class ChangeGUI extends JFrame {
        private JTextField eingeworfenField;
        private JLabel rueckGeldLabel;
        private DecimalFormat df = new DecimalFormat("#0.00"); // ❗ Rundet auf 2 Nachkommastellen

        public ChangeGUI() {
            setTitle("Rückgeld Rechner");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(null);

            getContentPane().setBackground(Color.GRAY);

            JLabel preisLabel = new JLabel("Der Preis beträgt: " + df.format(price) + " CHF");
            preisLabel.setBounds(50, 20, 200, 20);
            preisLabel.setForeground(Color.WHITE);
            add(preisLabel);

            JLabel eingeworfenLabel = new JLabel("Eingeworfen:");
            eingeworfenLabel.setBounds(50, 50, 100, 20);
            eingeworfenLabel.setForeground(Color.WHITE);
            add(eingeworfenLabel);

            eingeworfenField = new JTextField();
            eingeworfenField.setBounds(150, 50, 80, 20);
            eingeworfenField.setBackground(Color.LIGHT_GRAY);
            eingeworfenField.setForeground(Color.BLACK);
            add(eingeworfenField);

            JButton berechnenButton = new JButton("Berechnen");
            berechnenButton.setBounds(50, 80, 180, 30);
            berechnenButton.setBackground(Color.LIGHT_GRAY);
            berechnenButton.setForeground(Color.BLACK);
            add(berechnenButton);

            rueckGeldLabel = new JLabel("");
            rueckGeldLabel.setBounds(50, 120, 200, 20);
            rueckGeldLabel.setForeground(Color.WHITE);
            add(rueckGeldLabel);

            // Button-Listener für Berechnung
            berechnenButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        double eingeworfen = Double.parseDouble(eingeworfenField.getText());

                        // ❗ Fix: Direkt den Preis aus `price` nutzen
                        double preis = price;

                        if (preis <= 0) {
                            rueckGeldLabel.setText("❌ Fehler: Preis ungültig!");
                            return;
                        }

                        double rueckGeld = eingeworfen - preis;

                        if (rueckGeld < 0) {
                            rueckGeldLabel.setText("❌ Zu wenig Geld eingeworfen!");
                        } else {
                            rueckGeldLabel.setText("💰 Rückgeld: " + df.format(rueckGeld) + " CHF");
                            sp.buying = false;

                            // ❗ Fenster nach 2 Sekunden automatisch schließen
                            Timer timer = new Timer(2000, event -> {
                                dispose(); // Schließt dieses Fenster
                                SwingUtilities.getWindowAncestor(displayField).dispose(); // Schließt das NumPad
                            });
                            timer.setRepeats(false);
                            timer.start();
                        }
                    } catch (NumberFormatException ex) {
                        rueckGeldLabel.setText("❌ Ungültige Eingabe!");
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