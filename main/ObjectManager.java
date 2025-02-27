package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class ObjectManager {
    SnackPanel sp;
    private final BufferedImage[] objImage; // 1D-Array für Bilder
    public Objects[] object; // 1D-Array für Objekte
    public HashMap<Integer, SnackItem> snackItems; // Map für alle Snack-Objekte
    public String[][] objectNames; // 2D-Array für Namen nach Position

    public ObjectManager(SnackPanel sp) {
        this.sp = sp;
        object = new Objects[30];
        objImage = new BufferedImage[30];
        snackItems = new HashMap<>(); // Speichert alle Snacks nach Index
        objectNames = new String[5][5]; // 5x5 Raster für Snack-Namen

        load();
    }

    public void load() {
        // Snack-Items hinzufügen (Index, Name, Dateipfad, Preis)
        addSnack(0, "Bazooka Gum", "Bazooka Gum.png", 1.00, 0, 0, 0);
        addSnack(1, "Blauband", "Blauband.png", 8, 1, 0, 0);
        addSnack(2, "M&M's", "M&M's.png", 1.50, 2, 0, 0);
        addSnack(3, "Papes", "Papes.png", 1.20, 3, 0, 0);
        addSnack(4, "Powerade", "powerade.png", 2.00, 4, 0, 0);
        addSnack(5, "Pringles", "pringles.png", 2.80, 0, 1, 0);
        addSnack(6, "RedBull", "RedBull.png", 2.50, 1, 1, 0);
        addSnack(7, "Skittles", "Skittles.png", 1.75, 2, 1, 0);
        addSnack(8, "Sprite", "sprite.png", 1.80, 3, 1, 0);
        addSnack(9, "Takis", "Takis.png", 2.20, 4, 1, 0);
        addSnack(10, "Tiki Drink", "tiki drink.png", 2.20, 0, 2,0);
        addSnack(11, "Zweifel", "Zweifel.png", 2.30, 1, 2, 0);
        addSnack(12, "preg", "preg.png", 20, 3, 1, 0);
        addSnack(13, "chimpy", "chimpy.png", 2.50, 2, 1, 0);
        addSnack(14, "haribo", "Haribo.png", 2.80, 0, 2, 0);
        addSnack(15, "cola", "cola.png", 2.80, 0, 2, 0);
    }

    private void addSnack(int index, String name, String filePath, double price, int x, int y,int stock) {
        try {
            BufferedImage img = ImageIO.read(java.util.Objects.requireNonNull(getClass().getResourceAsStream("/res/OBJ/" + filePath)));
            objImage[index] = img;
            object[index] = new Objects(img);

            sp.item[index].name = name;
            sp.item[index].price = price;

            // Speichere das SnackItem und den Namen

            objectNames[x][y] = name; // Name an die Position speichern

            System.out.println("Geladen: " + name + " an Position [" + x + "][" + y + "]");
        } catch (IOException e) {
            System.out.println("FEHLER: Bild konnte nicht geladen werden: " + name);
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2, int x, int y, int index) {
        System.out.println("Wird geladen");
        if (index >= 0 && index < objImage.length && objImage[index] != null) {
            int screenX = getLocationX(x);
            int screenY = getLocationY(y);

            // Gegenstände um 20% vergrößert zeichnen
            int newSize = (int) (sp.tileSize * 1);
            g2.drawImage(objImage[index], screenX, screenY, newSize, newSize, null);

            // Zeigt den Namen und Preis über dem Objekt
            if (snackItems.containsKey(index)) {
                SnackItem item = snackItems.get(index);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 12));

                // Namen + Preis über dem Objekt anzeigen
                g2.drawString(item.name + " - " + item.price + "€", screenX, screenY - 5);
            }
        } else {
            System.out.println("WARNUNG: Kein Bild für Index " + index);
        }
    }

    public int getLocationX(int x) {
        return (16 * sp.scale) + (x * sp.tileSize);
    }

    public int getLocationY(int y) {
        return (29 * sp.scale) + (y * (sp.tileSize + (11 * sp.scale)));
    }
}
