package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;

// Represents a general game panel that encapsulates some of the shared funcationality between
// GamePanel and InventoryPanel.
public abstract class MainGamePanel extends JPanel {
    protected Game game;

    // EFFECTS: instantiates the panel given the game state, and creates a panel with
    //          the dimensions of the game, and a gray background.
    public MainGamePanel(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setBackground(Color.GRAY);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    // EFFECTS: does nothing. Inheritors of this class can choose to override it if necessary.
    public void updateButtonListeners() {
        //
    }
}
