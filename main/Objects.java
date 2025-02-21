package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Objects {
    SnackPanel sp = new SnackPanel();
    BufferedImage objImage[];
    public Objects(){

    }

    public void load() {
        try {

            //Objekte laden:
        objImage[0] = ImageIO.read(java.util.Objects.requireNonNull(getClass().getResource("/res/New Piskel.png")));


        } catch (IOException e) {
        e.printStackTrace();
        }
    }
    public void update() {

    }
    public void draw(){
        g2.drawImage(objImage[], 0, 0, screenWidth, screenHeight, null);
    }
}
