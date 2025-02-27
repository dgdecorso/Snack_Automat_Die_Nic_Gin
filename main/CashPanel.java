package main;

public class CashPanel {
    private SnackPanel sp; // Referenz auf das SnackPanel

    public CashPanel(SnackPanel sp) {
        this.sp = sp; // Bestehendes SnackPanel speichern
    }

    public double cashChecker(int x, int y) {
        // Stelle sicher, dass die Position gültig ist
        if (x >= 0 && x < sp.obj.objectNames.length && y >= 0 && y < sp.obj.objectNames[0].length) {
            String objectName = sp.obj.objectNames[x][y];

            if (objectName != null) {
                // Suche den Index des Namens in snackItems
                for (int index : sp.obj.snackItems.keySet()) {
                    if (sp.obj.snackItems.get(index).name.equals(objectName)) {
                        return sp.obj.snackItems.get(index).price; // Richtiges Objekt gefunden -> Preis zurückgeben
                    }
                }
            }
        }
        return -1; // Kein Produkt vorhanden
    }


    private int getIndexByName(String name) {
        for (int index : sp.obj.snackItems.keySet()) {
            if (sp.obj.snackItems.get(index).name.equals(name)) {
                return index;
            }
        }
        return -1; // Falls das Objekt nicht existiert
    }
}