package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class change extends JFrame {
    private JTextField eingeworfenField;
    private JLabel rueckGeldLabel;

    public change() {
        setTitle("Rückgeld Rechner");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Hintergrundfarbe für das Fenster
        getContentPane().setBackground(Color.GRAY);

        JLabel preisLabel = new JLabel("Der Preis beträgt:");
        preisLabel.setBounds(50, 20, 200, 20);
        preisLabel.setForeground(Color.DARK_GRAY);  // Schrift in dunklerem Grau
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
                    double preis = 2.5;
                    double rueckGeld = eingeworfen - preis;

                    if (rueckGeld < 0) {
                        rueckGeldLabel.setText("Zu wenig Geld eingeworfen!");
                    } else if (rueckGeld == 0) {
                        rueckGeldLabel.setText("Kein Rückgeld nötig. Danke!");
                    } else {
                        rueckGeldLabel.setText("Rückgeld: " + rueckGeld + " Fr.");
                    }
                } catch (NumberFormatException ex) {
                    rueckGeldLabel.setText("Ungültige Eingabe!");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            change gui = new change();
            gui.setVisible(true);
        });
    }
}