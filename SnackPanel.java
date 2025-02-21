import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class SnackPanel extends JPanel implements Runnable{
    //BACKGROUND
    private BufferedImage backgroundImage;

    //SCREEN
    public int originalTileSize = 32;
    public int scale = 3;
    public int tileSize = originalTileSize * scale;
    public int screenRows = 10;
    public int screenCols = 6;
    public int screenWidth = tileSize * screenCols;
    public int screenHeight = tileSize * screenRows;

    //FPS
    int FPS = 60;
    volatile boolean running = true;

    //SYSTEM
    Thread machineThread;
    public SnackPanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(54, 36, 25, 255));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.requestFocusInWindow();
        loadBackground();
    }
    public void setupMachine() {

    }
    public void startMachineThread() {
        machineThread = new Thread(this);
        machineThread.start();
    }
    public void loadBackground() {
       /* try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/background/image (3).png")));
        } catch (IOException e) {
            e.printStackTrace();
        } */
    }
    public void drawBackground(Graphics2D g2) {
        g2.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, null);
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
                SwingUtilities.invokeLater(() -> repaint()); // ✅ repaint() auf Swing-Thread setzen
                delta--;
            }
        }
    }

    private void update() {

    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawBackground(g2);
    }
}
