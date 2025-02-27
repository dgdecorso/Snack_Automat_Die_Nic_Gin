package main;

public class Admin extends AdminPanel {
    static SnackPanel sp = new SnackPanel();

    static int initialAmount = 10; // Anfangsbestand
    int currentAmount; // Aktueller Bestand

    public Admin() {
        // Setzt currentAmount auf den Bestand des ersten Snacks (Index 0)
        if (sp.objectManager.snackItems.containsKey(0)) {
            currentAmount = sp.objectManager.snackItems.get(0).stock;
        } else {
            currentAmount = 0; // Falls kein Snack vorhanden ist
        }
    }

    public void Refill(int index) {
        // Überprüfen, ob der Snack existiert
        if (sp.objectManager.snackItems.containsKey(index)) {
            sp.objectManager.snackItems.get(index).stock = initialAmount;
            currentAmount = initialAmount; // Setzt den aktuellen Bestand zurück
            System.out.println("Snack " + index + " wurde aufgefüllt auf " + initialAmount);
        } else {
            System.out.println("FEHLER: Snack mit Index " + index + " existiert nicht.");
        }
    }

    // Methode, um alle Snacks auf 10 aufzufüllen
    public static void RefillAll() {
        for (int index : sp.objectManager.snackItems.keySet()) {
            sp.objectManager.snackItems.get(index).stock = initialAmount;
        }
        System.out.println("Alle Snacks wurden wider aufgefüllt.");
    }
}