package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ObjectManager {
    SnackPanel sp;
    private final BufferedImage[] objImage; // Jetzt 1D
    public Objects[] object; // Jetzt 1D

    public ObjectManager(SnackPanel sp) {
        this.sp = sp;
        object = new Objects[30]; // Eindimensionales Array für Objekte
        objImage = new BufferedImage[30]; // Eindimensionales Array für Bilder

        load();
    }

    public int getLocationX(int x) {
        return (16 * sp.scale) + ((x) * sp.tileSize);
    }

    public int getLocationY(int y) {
        return (29 * sp.scale) + ((y) * (sp.tileSize + (11 * sp.scale)));
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

            // Erstelle ein neues transparentes BufferedImage
            BufferedImage transparentImg = new BufferedImage(sp.tileSize, sp.tileSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = transparentImg.createGraphics();
            g2d.setComposite(AlphaComposite.SrcOver); // Setzt die Transparenz-Korrektur
            g2d.drawImage(scaledImg, 0, 0, null);
            g2d.dispose();

            // Speichere das Bild in das 1D-Array
            objImage[index] = transparentImg;
            object[index] = new Objects(transparentImg);

            System.out.println("Geladen: " + imageName + " an Index: " + index);
        } catch (IOException e) {
            System.out.println("FEHLER: Bild konnte nicht geladen werden: " + imageName);
            e.printStackTrace();
        }
    }

    public void update() {
        // Falls später eine Update-Logik benötigt wird
    }

    public void draw(Graphics2D g2, int x, int y, int index) {
        // Sicherheit prüfen, um Abstürze zu vermeiden
        if (index >= 0 && index < objImage.length && objImage[index] != null) {
            int screenX = getLocationX(x);
            int screenY = getLocationY(y);
            g2.drawImage(objImage[index], screenX, screenY, null);
        } else {
            System.out.println("WARNUNG: Kein Bild für Index " + index);
        }
    }
}
