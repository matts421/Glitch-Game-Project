package ui;

import com.googlecode.lanterna.TextColor;
import model.*;

import javax.swing.*;
import java.awt.*;

// Represents an inventory panel to display the player's items.
public class InventoryPanel extends MainGamePanel {
    private static final int CELL_SIZE = 100;
    private Item coin = new Item("coin", TextColor.ANSI.GREEN, 0, 0);
    private JButton itemButton;
    private JButton healthButton;
    private JButton manaButton;
    private ItemButtonListener ibl;
    private PaymentButtonListener hbl;
    private PaymentButtonListener mbl;

    // EFFECTS: initializes the inventory panel and creates buttons for adding health, mana, and dropping coins
    public InventoryPanel(Game g) {
        super(g);
        createItemButton();
        createHealthButton();
        createManaButton();
        addButtons();
    }

    // MODIFIES: g
    // EFFECTS: Draws the current state of the inventory.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (itemButton.getParent() == null) {
            add(itemButton);
        }

        if (healthButton.getParent() == null) {
            add(healthButton);
        }

        if (manaButton.getParent() == null) {
            add(manaButton);
        }

        enableProperButtons();
        setButtonLocations();
        drawInventory(g);
    }

    // MODIFIES: this
    // EFFECTS: updates the button listeners to keep track of current game state.
    @Override
    public void updateButtonListeners() {
        ibl.setGame(game);
        hbl.setGame(game);
        mbl.setGame(game);
    }

    // MODIFIES: this
    // EFFECTS: adds the mana, add health, and drop item buttons to panel.
    private void addButtons() {
        add(itemButton);
        add(healthButton);
        add(manaButton);
    }

    // MODIFIES: this
    // EFFECTS: centres the button locations in the panel
    private void setButtonLocations() {
        itemButton.setLocation(Game.WIDTH / 2, 0);
        manaButton.setLocation(Game.WIDTH - manaButton.getWidth(), 0);
        healthButton.setLocation(0, 0);
    }

    // MODIFIES: this
    // EFFECTS: disables or enables the panel's buttons depending on the state of the game
    private void enableProperButtons() {
        if (!game.getPlayer().getInventory().getItems().contains(coin)) {
            itemButton.setEnabled(false);
        } else {
            itemButton.setEnabled(true);
        }

        if (game.getPlayer().getInventory().getQuantity(coin) >= 100) {
            if (game.getPlayer().getHealth() >= 5) {
                healthButton.setEnabled(false);
            } else {
                healthButton.setEnabled(true);
            }

            if (game.getPlayer().getMaxMana() >= 10) {
                manaButton.setEnabled(false);
            } else {
                manaButton.setEnabled(true);
            }

        } else {
            healthButton.setEnabled(false);
            manaButton.setEnabled(false);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the drop item button.
    private void createItemButton() {
        itemButton = new JButton("Drop item");
        ibl = new ItemButtonListener(game, new Item("coin", TextColor.ANSI.GREEN, 0,0));
        itemButton.addActionListener(ibl);
        itemButton.setSize(120, 30);
        itemButton.setFocusable(false);
    }

    // MODIFIES: this
    // EFFECTS: creates the add health button.
    private void createHealthButton() {
        healthButton = new JButton("Buy health [100 coins]");
        hbl = new PaymentButtonListener(game, 100, "health");
        healthButton.addActionListener(hbl);
        healthButton.setFocusable(false);
    }

    // MODIFIES: this
    // EFFECTS: creates the add mana button.
    private void createManaButton() {
        manaButton = new JButton("Increase max mana [100 coins]");
        mbl = new PaymentButtonListener(game, 100, "mana");
        manaButton.addActionListener(mbl);
        manaButton.setFocusable(false);
    }

    // MODIFIES: g
    // EFFECTS: draws the inventory
    private void drawInventory(Graphics g) {
        drawInventoryCells(g);
    }

    // MODIFIES: g
    // EFFECTS: draws an inventory cell for each item in the inventory.
    private void drawInventoryCells(Graphics g) {
        Color savedCol = g.getColor();
        g.setColor(new Color(52, 48, 48));
        int cursorX = CELL_SIZE;
        int cursorY = CELL_SIZE;

        for (Item item: game.getPlayer().getInventory().getItems()) {
            drawItemCell(g, cursorX, cursorY, item, game.getPlayer().getInventory().getQuantity(item));
            cursorX += CELL_SIZE * 3 / 2;
            if (cursorX + CELL_SIZE + 100 > Game.WIDTH) {
                cursorX = CELL_SIZE;
                cursorY += CELL_SIZE + 3 / 2;
            }

        }
        g.setColor(savedCol);
    }

    // REQUIRES: game.getPlayer().getInventory().getItems().contains(i)
    // MODIFIES: g
    // EFFECTS: draws a single item cell in the inventory.
    private void drawItemCell(Graphics g, int posX, int posY, Item i, int quantity) {
        Color savedCol = g.getColor();
        g.setColor(new Color(52, 48, 48));
        g.fillRect(posX, posY, CELL_SIZE, CELL_SIZE);
        g.setColor(i.getColor().toColor());
        g.fillOval(posX + CELL_SIZE / 8, posY + CELL_SIZE / 8,
                CELL_SIZE * 3 / 4, CELL_SIZE * 3 / 4);
        g.setColor(savedCol);
        g.drawString(i.getName().toUpperCase() + ": " + quantity, posX + CELL_SIZE / 4, posY);
    }
}
