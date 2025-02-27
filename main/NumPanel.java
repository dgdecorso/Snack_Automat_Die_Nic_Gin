package main;

public class NumPanel {
    public static String storedNumber = ""; // Variable zur Speicherung der Zahl

    public static void setNumber(String number) {
        storedNumber = number;
        System.out.println("Gespeicherte Nummer: " + storedNumber);
    }

    public static String getStoredNumber(String enteredNumber) {
        return storedNumber.isEmpty() ? enteredNumber : storedNumber;
    }
}
