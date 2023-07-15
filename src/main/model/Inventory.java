package model;

import java.util.HashMap;
import java.util.Set;

public class Inventory {
    HashMap<Item, Integer> items;

    public Inventory() {
        items = new HashMap<>();
    }

    // REQUIRES: getCapacity() - quantity >= 0
    //           AND quantity > 0
    // MODIFIES: this
    // EFFECTS: if item not already in inventory, adds item with quantity to items.
    //          Otherwise, adds quantity to existing quantity stored in inventory.
    public void addItem(Item item, int quantity) {
        // stub
    }

    // REQUIRES: getQuantity(item) >= quantity
    // MODIFIES: this
    // EFFECTS: removes quantity of item from inventory
    public void removeItem(Item item, int quantity) {
        //stub
    }

    public Set<Item> getItems() {
        return items.keySet();
    }

    // EFFECTS: returns number of times item appears in inventory.
    public int getQuantity(Item item) {
        return -1; //stub
    }


}
