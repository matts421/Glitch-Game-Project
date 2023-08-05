package ui;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.Item;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentButtonListener implements ActionListener {
    private Game game;
    private int cost;
    private String type;

    public PaymentButtonListener(Game g, int cost, String type) {
        game = g;
        this.cost = cost;
        this.type = type;
    }

    public void setGame(Game g) {
        game = g;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Item coin = new Item("coin", TextColor.ANSI.GREEN, 0, 0);
        game.getPlayer().getInventory().removeItem(coin, cost);
        if (type.equals("health")) {
            int currentHealth = game.getPlayer().getHealth();
            game.getPlayer().setHealth(currentHealth + 1);
        } else if (type.equals("mana")) {
            int currentMaxMana = game.getPlayer().getMaxMana();
            game.getPlayer().setMaxMana(currentMaxMana + 1);
        }
    }
}
