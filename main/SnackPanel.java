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
    // CASHPANEL
    public double cash = 23.43;

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

    // FPS
    int FPS = 60;
    volatile boolean running = true;

    // SYSTEM
    Thread machineThread;
    public SnackItem[] item;
    public ObjectManager obj; // Initialisiere NICHT direkt hier!

    // Variablen für das fallende Objekt
    private boolean isFalling = false;
    private int fallingObject = -1; // Welches Objekt fällt
    private int fallY = 0; // Y-Position des fallenden Objekts
    private long fallStartTime = 0; // Startzeit des Falls

    // NumPad
    private NumPad numPad;
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


        // Initialisiere das Item-Array VOR dem ObjectManager!
        item = new SnackItem[16];
        for (int i = 0; i < item.length; i++) {
            item[i] = new SnackItem();
        }

        // Jetzt ObjectManager initialisieren
        obj = new ObjectManager(this);

        // Calculate NumPad Image size
        padWidth = screenWidth / 4;
        padHeight = screenHeight / 5;

        // Add MouseListener for clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                if (isNumPadClicked(mouseX, mouseY)) {
                    toggleNumPad();
                }
            }
        });
    }

    public void setupMachine() {
        item = new SnackItem[16];
        for (int i = 0; i < item.length; i++) {
            item[i] = new SnackItem();
        }

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
       if (isFalling) {
           updateFall();
       }
    }

    // FALLING OBJECT ANIMATION
    public void fallingObject(int index) {
        System.out.println("In falling object");

            isFalling = true;
            fallingObject = index;
            fallY = obj.getLocationY(index / 4); // Setze die Startposition
            fallStartTime = System.currentTimeMillis(); // Starte den Timer
            updateFall();

    }


    private void updateFall() {
        while (isFalling) {
            long elapsedTime = System.currentTimeMillis() - fallStartTime;

            if (elapsedTime < 2000) { // Erste 2 Sekunden: Langsames Fallen (3px)
                fallY += 3;
            } else if (elapsedTime < 700000000) { // Danach schnelles Fallen (750px in 5s)
                fallY += 750 / 50; // ~15 px pro Frame
            }
            if (fallY >= 800) { // Falls es unter 800 px ist, 0stoppe das Fallen
                isFalling = false;
                fallingObject = -1;
                fallY = 0; // Zurücksetzen für das nächste Objekt
            }

            repaint(); // Neu zeichnen
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawBackground(g2);
        drawPad(g2);

        int index = 0;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (fallingObject == index) {
                    obj.draw(g2, x, fallY / tileSize, index); // Zeichne das fallende Objekt
                } else {
                    obj.draw(g2, x, y, index); // Zeichne alle anderen
                }
                index++;
            }
        }
    }

    private boolean isNumPadClicked(int x, int y) {
        return (x >= padX && x <= padX + padWidth) && (y >= padY && y <= padY + padHeight);
    }

    private void toggleNumPad() {
        SwingUtilities.invokeLater(() -> {
            if (numPad == null) {
                numPad = new NumPad();
            }
            numPad.setVisible(!numPad.isVisible());
        });
    }
}
