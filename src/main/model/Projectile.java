package model;

import com.googlecode.lanterna.TextColor;

import java.awt.*;

/*
    Represents a projectile that can be summoned by the player to kill enemies.
 */
public class Projectile {
    public static final int PROJECTILE_SPEED = 1;
    public static final char LEFT_PROJECTILE = '←';
    public static final char RIGHT_PROJECTILE = '→';
    private TextColor color;
    private int posX;
    private int posY;
    private int direction;
    private Rectangle model;

    // EFFECTS: creates new projectile with position, direction, and color equivalent to
    //          player's position and direction. The hit-box of the projectile
    //          is a single pixel centered around the x/y position.
    public Projectile(GameCharacter player) {
        color = player.getColor();
        posX = player.getPosX();
        posY = player.getPosY();
        direction = player.getDirection();
        model = new Rectangle(posX, posY, 1, 1);
    }

    // MODIFIES: this
    // EFFECTS: moves projectile by missile speed in original
    //          player direction.
    public void move() {
        posX += PROJECTILE_SPEED * direction;
        model.x = posX;
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

    public int getDirection() {
        return direction;
    }

    public Rectangle getModel() {
        return model;
    }
}
