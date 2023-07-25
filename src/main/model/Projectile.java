package model;

import com.googlecode.lanterna.TextColor;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;

/*
    Represents a projectile that can be summoned by the player to kill enemies.
 */
public class Projectile extends HasModel implements Writable {
    public static final int PROJECTILE_SPEED = 1;
    public static final char LEFT_PROJECTILE = '<';
    public static final char RIGHT_PROJECTILE = '>';
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

    // EFFECTS: creates new projectile with position, direction, and color equivalent to
    //          player's position and direction. The hit-box of the projectile
    //          is a single pixel centered around the x/y position.
    public Projectile(int x, int y, int direction, TextColor color, Rectangle model) {
        this.color = color;
        posX = x;
        posY = y;
        this.direction = direction;
        this.model = model;
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("color", rgbToJson(color));
        json.put("direction", direction);
        json.put("position", createPosition(posX, posY));
        json.put("model", rectangleToJson(model));
        return json;
    }

}
