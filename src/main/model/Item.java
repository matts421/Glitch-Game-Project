package model;

import com.googlecode.lanterna.TextColor;

import java.awt.*;

/*
    Represents an item with a unique color and name.
 */
public class Item {
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
        model = new Rectangle(x, y, 1, 1);
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
}
