//Könnt Ihr hier bitte noch die Arrays und Strings anpassen auf den Teil,
//welchen man sebst gecoded hat.


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SnackAutomat extends JFrame {
    private JPanel buttonPanel, displayPanel;
    private Map<Integer, Point> snackPositions; // Speichert Positionen der Snacks
    private JLabel fallingSnack;
    private Timer timer;
    private boolean isFalling = false; // Verhindert gleichzeitige Snacks die fallen

    public SnackAutomat() {
        setTitle("Snackautomat");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel für das Nummern-Pad
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));

        // Buttons für das Nummern-Pad (1-9)
        for (int i = 1; i <= 9; i++) {
            JButton button = new JButton(String.valueOf(i));
            int snackNum = i; // Speichert die aktuelle Nummer für den Listener
            button.addActionListener(e -> dropSnack(snackNum));
            buttonPanel.add(button);
        }

        // Panel für fallende Snacks
        displayPanel = new JPanel();
        displayPanel.setBackground(Color.LIGHT_GRAY);
        displayPanel.setLayout(null);

        add(buttonPanel, BorderLayout.NORTH);
        add(displayPanel, BorderLayout.CENTER);

        // Snack-Positionen initialisieren
        initializeSnackPositions();

        setVisible(true);
    }

    // Definiert die Startpositionen für alle 32 Snacks
    private void initializeSnackPositions() {
        snackPositions = new HashMap<>();

        int x = 50;
        int y = 50;
        for (int i = 1; i <= 32; i++) {
            snackPositions.put(i, new Point(x, y));
            x += 100;
            if (x > 400) {
                x = 50;
                y += 50;
            }
        }
    }

    // Startet das Fallen eines Snacks (aber nur, wenn keiner fällt)
    private void dropSnack(int snackNumber) {
        if (isFalling || !snackPositions.containsKey(snackNumber)) return; // Falls schon ein Snack fällt

        isFalling = true; // Sperrt neue Falls
        Point startPos = snackPositions.get(snackNumber);

        // Falls bereits ein Snack-Label existiert, entferne es
        if (fallingSnack != null) {
            displayPanel.remove(fallingSnack);
        }

        // Neues Snack-Label erstellen
        fallingSnack = new JLabel();
        fallingSnack.setOpaque(true);
        fallingSnack.setBackground(Color.RED); // Farbe als Platzhalter
        fallingSnack.setBounds(startPos.x, startPos.y, 50, 50);
        displayPanel.add(fallingSnack);
        displayPanel.repaint();

        // Timer für die Fallbewegung
        timer = new Timer(30, new ActionListener() {
            int yPos = startPos.y;

            @Override
            public void actionPerformed(ActionEvent e) {
                yPos += 5;
                fallingSnack.setBounds(startPos.x, yPos, 50, 50);

                if (yPos >= 400) { // Unten angekommen
                    timer.stop();
                    isFalling = false; // Nächster Snack kann fallen
                }
            }
        });

        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SnackAutomat::new);
    }
}
