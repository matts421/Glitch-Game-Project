package model;

import com.googlecode.lanterna.TextColor;

import java.awt.*;

/*
    Represents an enemy that the player will kill in the game.
 */
public class Enemy {
    public static final char RENDER_MODEL = '☠';
    public static final int DAMAGE = 1;
    private TextColor color;
    private int posX;
    private int posY;
    private Rectangle model;

    // EFFECTS: creates new enemy with x-position x, y-position y,
    //          magenta color, and a single-pixel hit-box centered at x/y position.
    public Enemy(int x, int y) {
        posX = x;
        posY = y;
        color = TextColor.ANSI.MAGENTA;
        model = new Rectangle(posX, posY, 1, 1);
    }

    public int getPosY() {
        return posY;
    }

    public int getPosX() {
        return posX;
    }

    public TextColor getColor() {
        return color;
    }

    public Rectangle getModel() {
        return model;
    }
}
