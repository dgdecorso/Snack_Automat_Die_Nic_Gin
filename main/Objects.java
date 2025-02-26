package main;

import java.awt.image.BufferedImage;

public class Objects {

    private final BufferedImage objImage;
    public BufferedImage image;
    public boolean collision = false;
    public Objects(BufferedImage image) {
        this.objImage = image;
    }
}