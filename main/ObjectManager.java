package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.IOException;

public class ObjectManager {
    SnackPanel sp;
    private final BufferedImage[][] objImage;
    public Objects[][] object;

    public ObjectManager(SnackPanel sp) {
        this.sp = sp;
        object = new Objects[30][30];
        objImage = new BufferedImage[30][30];

        load();
    }

    public int getLocationX(int x) {
        return (16 * sp.scale) + (x * sp.tileSize) - (2 * sp.scale);
    }

    public int getLocationY(int y) {
        return (16 * sp.scale) + (y * sp.tileSize) - (2 * sp.scale);
    }

    public void load() {
        UtilityTool uTool = new UtilityTool();
        setup(0, "M&M's", uTool);
        setup(1, "powerade", uTool);
        setup(2, "RedBull", uTool);
        setup(3, "Skittles", uTool);
        setup(4, "sprite", uTool);
        setup(5, "tiki drink", uTool);
    }

    private void setup(int index, String imageName, UtilityTool uTool) {
        try {
            BufferedImage img = ImageIO.read(java.util.Objects.requireNonNull(getClass().getResourceAsStream("/res/OBJ/" + imageName + ".png")));
            BufferedImage scaledImg = uTool.scaleImage(img, sp.tileSize, sp.tileSize);

            GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration();
            VolatileImage vImg = gc.createCompatibleVolatileImage(sp.tileSize, sp.tileSize, Transparency.TRANSLUCENT);

            Graphics2D g2d = vImg.createGraphics();
            g2d.drawImage(scaledImg, 0, 0, null);
            g2d.dispose();

            // Stelle sicher, dass Arrays initialisiert sind
            for (int y = 0; y < 30; y++) {
                for (int x = 0; x < 30; x++) {
                    objImage[y][x] = null;
                }
            }

            // Bild speichern
            objImage[index][0] = vImg.getSnapshot();
            object[index][0] = new Objects(vImg.getSnapshot());

            System.out.println("Geladen: " + imageName + " an Index: " + index);
        } catch (IOException e) {
            System.out.println("FEHLER: Bild konnte nicht geladen werden: " + imageName);
            e.printStackTrace();
        }
    }

    public void update() {
        // Falls später eine Update-Logik benötigt wird
    }

    public void draw(Graphics2D g2, int x, int y) {
        if (x >= 0 && x < objImage.length && y >= 0 && y < objImage[x].length && objImage[x][y] != null) {
            int screenX = getLocationX(x);
            int screenY = getLocationY(y);
            g2.drawImage(objImage[x][y], screenX, screenY, null);
        } else {
            System.out.println("WARNUNG: objImage[" + x + "][" + y + "] ist null!");
        }
    }
}
