package main;

import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        window.setResizable(false);
        window.setTitle("SnackAutomat");
        window.setBackground(Color. white);

        SnackPanel sp = new SnackPanel();
        window.add(sp);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);


        sp.setupMachine();
        sp.startMachineThread();


    }
}