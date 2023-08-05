package ui;

import model.Game;
import model.Item;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Button listener for dropping items from inventory.
public class ItemButtonListener implements ActionListener {
    private Item item;
    private Game game;

    // EFFECTS: instantiates button listener with game state and item to drop
    public ItemButtonListener(Game game, Item item) {
        this.item = item;
        this.game = game;
    }

    public void setGame(Game g) {
        game = g;
    }

    // MODIFIES: this
    // EFFECTS: removes item from player's inventory, and places it in the map next to the player on button press.
    @Override
    public void actionPerformed(ActionEvent e) {
        game.getPlayer().getInventory().removeItem(item, 1);
        int playerX = game.getPlayer().getPosX();
        int playerY = game.getPlayer().getPosY();
        Item mapItem = new Item(item.getName(), item.getColor(),
                playerX + game.getPlayer().getModel().width * game.getPlayer().getDirection(), playerY);
        game.getMap().getItems().addItem(mapItem, 1);
    }
}
