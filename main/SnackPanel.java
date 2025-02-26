package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class SnackPanel extends JPanel implements Runnable {
    // BACKGROUND
    private BufferedImage backgroundImage;
    private BufferedImage NumPadImage;
    private BufferedImage spring;

    // SCREEN
    public int originalTileSize = 32;
    public int scale = 3;
    public int tileSize = originalTileSize * scale;
    public int screenRows = 10;
    public int screenCols = 6;
    public int screenWidth = tileSize * screenCols;
    public int screenHeight = tileSize * screenRows;
    public ObjectManager obj = new ObjectManager(this);

    // FPS
    int FPS = 60;
    volatile boolean running = true;

    // SYSTEM
    Thread machineThread;

    // NumPad
    private NumPad numPad; // Korrekt als Instanzvariable gespeichert
    private final int padX = 440;
    private final int padY = 2 * tileSize;
    private final int padWidth;
    private final int padHeight;

    public SnackPanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(137, 137, 137, 255));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.requestFocusInWindow();

        // Calculate NumPad Image size
        padWidth = screenWidth / 4;
        padHeight = screenHeight / 5;

        // Add MouseListener for clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                // Check if click is on NumPad image
                if (isNumPadClicked(mouseX, mouseY)) {
                    toggleNumPad(); // Richtig aufrufen
                }
            }
        });
    }

    public void setupMachine() {
        loadBackground();
        loadNumPad();
        obj.load();
    }

    public void startMachineThread() {
        machineThread = new Thread(this);
        machineThread.start();
    }

    public void loadBackground() {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/res/New Piskel (1).png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSpring() {
        try {
            spring = ImageIO.read(Objects.requireNonNull(getClass().getResource("/res/OBJ/New Piskel (1).png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadNumPad() {
        try {
            NumPadImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/res/NumClick.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawBackground(Graphics2D g2) {
        g2.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, null);
    }

    public void drawSpring(Graphics2D g2,int x,int y) {
        g2.drawImage(spring, x, y, null);
    }

    public void drawPad(Graphics2D g2) {
        g2.drawImage(NumPadImage, padX, padY, padWidth, padHeight, null);
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (running) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                SwingUtilities.invokeLater(this::repaint);
                delta--;
            }
        }
    }

    private void update() {
        // Game logic updates (if needed)
    }

    @Override
    public void paintComponent(Graphics g) {
        int index = 0;
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawBackground(g2);
        drawPad(g2);
        //Objects
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                obj.draw(g2, x, y, index);
                index++;
            }
        }
        //Metall Federn
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {

            }
        }
    }

    /**
     * Check if the mouse click happened inside the NumPad image area.
     */
    private boolean isNumPadClicked(int x, int y) {
        return (x >= padX && x <= padX + padWidth) && (y >= padY && y <= padY + padHeight);
    }

    /**
     * Open or close the NumPad window when the image is clicked.
     */
    private void toggleNumPad() {
        SwingUtilities.invokeLater(() -> {
            if (numPad == null) {
                numPad = new NumPad();
            }

            if (numPad.isVisible()) {
                numPad.setVisible(false); // Fenster verstecken
            } else {
                numPad.setVisible(true); // Fenster anzeigen
            }
        });
    }
}
