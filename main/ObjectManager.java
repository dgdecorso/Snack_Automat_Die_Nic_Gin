package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.IOException;

public class ObjectManager {
    SnackPanel sp;
    private BufferedImage[][] objImage;
    public Objects[][] object;

    public ObjectManager(SnackPanel sp) {
        this.sp = sp;
        object = new Objects[5][5];
        objImage = new BufferedImage[5][5]; // Initialisierung des Arrays

        load();
    }

    public void load() {
        UtilityTool uTool = new UtilityTool();
        setup(0, "M&M's", false, uTool); // Sicherstellen, dass der Bildname gültig ist
    }

    private void setup(int index, String imageName, boolean collision, UtilityTool uTool) {
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

            // Sicherstellen, dass das Array nicht null ist
            object[index] = new Objects[1];
            objImage[index] = new BufferedImage[5];

            // Bild speichern
            objImage[index][0] = vImg.getSnapshot();
            object[index][0] = new Objects(vImg.getSnapshot());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // Update-Logik hinzufügen, falls nötig
    }

    public void draw(Graphics2D g2) {
        if (objImage[0][0] != null) {
            g2.drawImage(objImage[0][0], sp.tileSize, sp.tileSize, null);
        }
    }
}
