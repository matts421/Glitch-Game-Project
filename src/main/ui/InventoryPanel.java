package ui;

import com.googlecode.lanterna.TextColor;
import model.*;

import javax.swing.*;
import java.awt.*;

public class InventoryPanel extends MainGamePanel {
    private static final int CELL_SIZE = 100;
    private Item coin = new Item("coin", TextColor.ANSI.GREEN, 0, 0);
    private JButton itemButton;
    private JButton healthButton;
    private JButton manaButton;
    private ItemButtonListener ibl;
    private PaymentButtonListener hbl;
    private PaymentButtonListener mbl;

    public InventoryPanel(Game g) {
        super(g);
        createItemButton();
        createHealthButton();
        createManaButton();
        addButtons();
    }

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

    @Override
    public void updateButtonListeners() {
        ibl.setGame(game);
        hbl.setGame(game);
        mbl.setGame(game);
    }

    private void addButtons() {
        add(itemButton);
        add(healthButton);
        add(manaButton);
    }

    private void setButtonLocations() {
        itemButton.setLocation(Game.WIDTH / 2, 0);
        manaButton.setLocation(Game.WIDTH - manaButton.getWidth(), 0);
        healthButton.setLocation(0, 0);
    }

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

    private void createItemButton() {
        itemButton = new JButton("Drop item");
        ibl = new ItemButtonListener(game, new Item("coin", TextColor.ANSI.GREEN, 0,0));
        itemButton.addActionListener(ibl);
        itemButton.setSize(120, 30);
        itemButton.setFocusable(false);
    }

    private void createHealthButton() {
        healthButton = new JButton("Buy health [100 coins]");
        hbl = new PaymentButtonListener(game, 100, "health");
        healthButton.addActionListener(hbl);
        healthButton.setFocusable(false);
    }

    private void createManaButton() {
        manaButton = new JButton("Increase max mana [100 coins]");
        mbl = new PaymentButtonListener(game, 100, "mana");
        manaButton.addActionListener(mbl);
        manaButton.setFocusable(false);
    }

    private void drawInventory(Graphics g) {
        drawInventoryCells(g);
    }

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
