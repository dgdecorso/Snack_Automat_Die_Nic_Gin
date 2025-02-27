package main;

public class NumPanel {
    public static String storedNumber = ""; // Variable zur Speicherung der Zahl
    public static SnackPanel snackPanel; // Referenz zum Panel

    public static void setNumber(String number) {
        storedNumber = number;
        System.out.println("Gespeicherte Nummer: " + storedNumber);

        if (snackPanel != null) {
            snackPanel.repaint(); // SnackPanel neu zeichnen
        }
    }

    public static String getStoredNumber() {
        return storedNumber;
    }
}
