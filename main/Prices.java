package main;

import java.util.HashMap;
import java.util.Map;

public class Prices {
    public static final Map<String, Double> priceMap = new HashMap<>();
    // Preise für jede mögliche Kombination definieren
    static {
        priceMap.put("11", 2.50);
        priceMap.put("12", 3.00);
        priceMap.put("13", 1.50);
        priceMap.put("14", 4.00);
        priceMap.put("21", 2.00);
        priceMap.put("22", 2.80);
        priceMap.put("23", 3.50);
        priceMap.put("24", 4.20);
        // ... weitere Kombinationen hinzufügen ...
    }


    // Methode zur Preisberechnung
    public static double priceCalculator() {
        String storedNumber = NumPanel.getStoredNumber();
        return priceMap.getOrDefault(storedNumber, -1.0); // -1.0 wenn Nummer nicht existiert
    }
}
