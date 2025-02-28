package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SnackPanel extends JPanel implements Runnable {
    // CASHPANEL
    public String cash = NumPanel.storedNumber; // Standardwert
    public double priceItem;
    public boolean buying = false;
    // BACKGROUND
    private BufferedImage backgroundImage;
    private BufferedImage NumPadImage;
    private BufferedImage spring;
    private BufferedImage front;
    private Font pixelFont;

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
    public ObjectManager obj;

    // States
    public boolean isFalling = false;
    public int fallingObject = 999;
    public ObjectManager objectManager;

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

        // Initialisiere das Item-Array
        item = new SnackItem[16];
        for (int i = 0; i < item.length; i++) {
            item[i] = new SnackItem();
        }

        // Initialisiere ObjectManager
        obj = new ObjectManager(this);

        // NumPad Image size
        padWidth = screenWidth / 4;
        padHeight = screenHeight / 5;

        // Lade die Pixel-Font
       // loadPixelFont();

        // MouseListener für NumPad-Klicks
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

    private void loadPixelFont() {
        try {
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/Arvo,Courier_Prime,Pixelify_Sans/Pixelify_Sans/PixelifySans-VariableFont_wght.ttf"));
            pixelFont = pixelFont.deriveFont(20f); // Schriftgröße anpassen
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            pixelFont = new Font("Monospaced", Font.BOLD, 20); // Fallback
        }
    }

    public void setupMachine() {
        loadBackground();
        loadFront();
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

    public void loadFront() {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/res/finished2.png")));
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

    public void drawSpring(Graphics2D g2, int x, int y) {
        g2.drawImage(spring, x, y, null);
    }

    public void drawPad(Graphics2D g2) {
        g2.drawImage(NumPadImage, padX, padY, padWidth, padHeight, null);
    }

    public void drawFront(Graphics2D g2) {
        g2.drawImage(front, 0, 0, screenWidth, screenHeight, null);
    }

    public void drawFront(Graphics2D g2, int x, int y) {
        g2.drawImage(front, 0, 0, screenWidth, screenHeight, null);
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
        cash = NumPanel.storedNumber; // Holt den aktuellen Preis aus NumPanel
        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        int index = 0;
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                obj.draw(g2, x, y, index);
                index++;
            }
        }
        drawBackground(g2);
        drawPad(g2);
        if (buying) {
        drawPrice(g2); // Preis oben rechts anzeigen
        }


    }

    /**
     * Zeigt den aktuellen Preis oben rechts im Fenster an.
     */
    public void drawPrice(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(pixelFont);



        String priceText = "€" + String.format("%.2f", priceItem); // Formatiert auf 2 Nachkommastellen
        int textX = screenWidth - 95;
        int textY = 150;
        g2.drawString(priceText, textX, textY);
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
