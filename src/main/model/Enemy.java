package model;

import com.googlecode.lanterna.TextColor;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;

/*
    Represents an enemy that the player will kill in the game.
 */
public class Enemy extends HasModel implements Writable {
    public static final char RENDER_MODEL = '@';
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

    // EFFECTS: creates new enemy with x-position x, y-position y,
    //          magenta color, and a single-pixel hit-box centered at x/y position.
    public Enemy(int x, int y, Rectangle model) {
        posX = x;
        posY = y;
        color = new TextColor.RGB(255, 0, 255);
        this.model = model;
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("position", createPosition(posX, posY));
        json.put("model", rectangleToJson(model));
        return json;
    }
}
