package main;
import java.util.Scanner;

public class change {
    public static void main(String[] args){
        System.out.println("Der Preis beträgt 2.5 Fr.");
        System.out.println("Werfen Sie bitte das Geld ein");
        Scanner scanner = new Scanner(System.in);
        double eingeworfen = scanner.nextDouble();
        double preis = 2.5;
        double rueckGeld = eingeworfen - preis;

        if(rueckGeld < 0){
            System.out.println("Sie haben zu wenig Geld eingeworfen! ");
        }else if (rueckGeld == 0){
            System.out.println("Kein Rückgeld nötig. Danke für Ihren einkauf!");
        }else{
            System.out.println("Rückgeld: " + rueckGeld + "Fr.");
        }

    }
}
