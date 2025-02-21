import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        window.setResizable(false);
        window.setTitle("Snack Automat");
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