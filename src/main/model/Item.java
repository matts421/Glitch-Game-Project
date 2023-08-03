package model;

import com.googlecode.lanterna.TextColor;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;
import java.util.Objects;

/*
    Represents an item with a unique color and name.
 */
public class Item extends HasModel implements Writable {
    private String name;
    private TextColor color;
    private int posX;
    private int posY;
    private Rectangle model;

    // EFFECTS: creates item with name, color, x-position and y-position.
    //          Item model is a single pixel centered at x/y position.
    public Item(String name, TextColor color, int x, int y) {
        this.name = name;
        this.color = color;
        posX = x;
        posY = y;
        model = new Rectangle(x, y, Game.UP_SCALE, Game.UP_SCALE);
    }

    // EFFECTS: creates item with name, color, x-position, y-position, and model.
    public Item(String name, TextColor color, int x, int y, Rectangle model) {
        this.name = name;
        this.color = color;
        posX = x;
        posY = y;
        this.model = model;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public TextColor getColor() {
        return color;
    }

    public Rectangle getModel() {
        return model;
    }

    public String getName() {
        return name;
    }

    // EFFECTS: overrides default equals() functionality to compare item name instead of the object itself
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(name, item.name);
    }

    // EFFECTS: overrides default hashCode() functionality to compare item name instead of the object itself
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }



    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("color", rgbToJson(color));
        json.put("position", createPosition(posX, posY));
        json.put("model", rectangleToJson(model));
        return json;
    }
}
