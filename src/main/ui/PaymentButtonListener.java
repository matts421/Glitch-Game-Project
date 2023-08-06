package ui;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.Item;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a button listener for the buttons that purchase mana or health.
public class PaymentButtonListener implements ActionListener {
    private Game game;
    private int cost;
    private String type;

    // EFFECTS: instantiates the listener with the current game state, cost of the transaction,
    //          and a type of purchasing mana or health
    public PaymentButtonListener(Game g, int cost, String type) {
        game = g;
        this.cost = cost;
        this.type = type;
    }

    public void setGame(Game g) {
        game = g;
    }

    // REQUIRES: game.getPlayer().getInventory().getQuantity(coin) >= cost
    // MODIFIES: this
    // EFFECTS: removes the cost of the transaction from the player's coins and
    //          updates the health or mana of the player depending on type.
    @Override
    public void actionPerformed(ActionEvent e) {
        Item coin = new Item("coin", TextColor.ANSI.GREEN, 0, 0);
        game.getPlayer().loseItem(coin, cost);
        if (type.equals("health")) {
            int currentHealth = game.getPlayer().getHealth();
            game.getPlayer().setHealth(currentHealth + 1);
        } else if (type.equals("mana")) {
            int currentMaxMana = game.getPlayer().getMaxMana();
            game.getPlayer().setMaxMana(currentMaxMana + 1);
        }
    }
}
