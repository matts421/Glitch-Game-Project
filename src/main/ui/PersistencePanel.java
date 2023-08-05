package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;

// A parent class that contains some of the shared responsibilities of the load and save panels.
public abstract class PersistencePanel extends JPanel {
    protected GuiGame guiGame;
    protected PersistenceButtonListener confirmLoadListener;
    protected PersistenceButtonListener cancelLoadListener;
    protected PersistenceButtonListener confirmSaveListener;
    protected PersistenceButtonListener cancelSaveListener;

    protected JButton confirmButton;
    protected JButton cancelButton;

    // EFFECTS: instantiates a persistence panel and creates listeners for loading and saving buttons.
    public PersistencePanel(GuiGame guiGame) {
        this.guiGame = guiGame;
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setBackground(Color.GRAY);
        confirmLoadListener = new PersistenceButtonListener(guiGame, "load", true);
        confirmSaveListener = new PersistenceButtonListener(guiGame, "save", true);
        cancelLoadListener = new PersistenceButtonListener(guiGame, "load", false);
        cancelSaveListener = new PersistenceButtonListener(guiGame, "save", false);
    }

    // MODIFIES: g
    // EFFECTS: draws the text in str onto g at position y, using font metrics of fm
    // NOTE: this method has been directly reproduced from SpaceInvaders
    public void centreString(String str, Graphics g, FontMetrics fm, int posY) {
        int width = fm.stringWidth(str);
        g.drawString(str, (Game.WIDTH - width) / 2, posY);
    }

    // MODIFIES: this
    // EFFECTS: creates confirm and cancel buttons
    public abstract void createButtons();
}
