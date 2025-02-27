
package main;

public class Admin extends AdminPanel {
    SnackPanel sp = new SnackPanel();

    int initialAmount = 10; // Anfangsbestand
    int currentAmount; // Aktueller Bestand

    public Admin() {
        // Setzt currentAmount auf den Bestand des ersten Snacks (Index 0)
        if (sp.obj.snackItems.containsKey(0)) {
            currentAmount = sp.obj.snackItems.get(0).stock;
        } else {
            currentAmount = 0000; // Falls kein Snack vorhanden ist
        }
    }

    public void Refill(int index) {
        // Überprüfen, ob der Snack existiert
        if (sp.obj.snackItems.containsKey(index)) {
            sp.obj.snackItems.get(index).stock = initialAmount;
            currentAmount = initialAmount; // Setzt den aktuellen Bestand zurück
            System.out.println("Snack " + index + " wurde aufgefüllt auf " + initialAmount);
        } else {
            System.out.println("FEHLER: Snack mit Index " + index + " existiert nicht.");
        }
    }
}
