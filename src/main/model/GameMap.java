package model;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameMap {
    private ArrayList<Rectangle> barriers;
    private Inventory items;

    // EFFECTS: creates a map of the level stage including specified
    //          barriers and items
    public GameMap(ArrayList<Rectangle> barriers,
                   Inventory items) {
        this.barriers = barriers;
        this.items = items;
    }

    // REQUIRES: items.getQuantity(item) >= 1
    // MODIFIES: this
    // EFFECTS: remove a single item from the map
    public void removeItem(Item item) {
        items.removeItem(item, 1);
    }

    public ArrayList<Rectangle> getBarriers() {
        return barriers;
    }

    public Inventory getItems() {
        return items;
    }

}
