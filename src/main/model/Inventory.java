package model;

import java.util.HashMap;
import java.util.Set;

/*
    Acts as a container for both the map and player's items.
 */
public class Inventory {
    private HashMap<Item, Integer> items;

    // EFFECTS: creates new inventory represented as a hashmap of items.
    public Inventory() {
        items = new HashMap<>();
    }


    // REQUIRES: getCapacity() - quantity >= 0
    //           AND quantity > 0
    // MODIFIES: this
    // EFFECTS: if item not already in inventory, adds item with quantity to items.
    //          Otherwise, adds quantity to existing quantity stored in inventory.
    public void addItem(Item item, int quantity) {
        if (items.containsKey(item)) {
            int oldQuantity = items.get(item);
            items.replace(item, oldQuantity + quantity);
        } else {
            items.put(item, quantity);
        }
    }

    // REQUIRES: getQuantity(item) >= quantity
    // MODIFIES: this
    // EFFECTS: removes quantity of item from inventory.
    public void removeItem(Item item, int quantity) {
        int oldQuantity = items.get(item);
        items.replace(item, oldQuantity - quantity);
        if (getQuantity(item) == 0) {
            items.remove(item);
        }
    }

    // EFFECTS: returns number of times item appears in inventory.
    public int getQuantity(Item item) {
        return items.get(item);
    }

    // EFFECTS: returns number of times item with name itemName appears in inventory.
    public int getQuantity(String itemName) {
        int totalQuantity = 0;
        for (Item item: items.keySet()) {
            if (item.getName().equals(itemName)) {
                totalQuantity += items.get(item);
            }
        }
        return totalQuantity;
    }

    public Set<Item> getItems() {
        return items.keySet();
    }


}
