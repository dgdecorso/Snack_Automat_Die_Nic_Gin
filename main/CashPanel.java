package main;

public class CashPanel {
    private SnackPanel sp; // Referenz auf das SnackPanel

    public CashPanel(SnackPanel sp) {
        this.sp = sp; // Bestehendes SnackPanel speichern
    }

    public double cashChecker(int x, int y) {
        if (sp.obj.objectNames[x][y] != null) { // Falls ein Objekt existiert
            int index = getIndexByName(sp.obj.objectNames[x][y]);

            if (sp.obj.snackItems.containsKey(index)) {
                return sp.obj.snackItems.get(index).price; // Preis zurückgeben
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