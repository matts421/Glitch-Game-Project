package model;

import java.awt.*;

public class Item {
    private String name;
    private Color color;
    private int posX;
    private int posY;

    public Item(String name, Color color, int x, int y) {
        this.name = name;
        this.color = color;
        posX = x;
        posY = y;
    }
}
