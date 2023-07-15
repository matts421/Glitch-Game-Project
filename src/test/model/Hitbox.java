package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Hitbox {

    public int getAlpha(int xPixel, int yPixel, BufferedImage img) {
        Color color = new Color(img.getRGB(xPixel, yPixel));
        return color.getAlpha();
    }

    public boolean isAlpha(int xPixel, int yPixel, BufferedImage img) {
        int alpha = getAlpha(xPixel, yPixel, img);
        return alpha == 0;
    }
}
