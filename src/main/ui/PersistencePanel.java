package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;

public abstract class PersistencePanel extends JPanel {
    protected GuiGame guiGame;
    protected PersistenceButtonListener confirmLoadListener;
    protected PersistenceButtonListener cancelLoadListener;
    protected PersistenceButtonListener confirmSaveListener;
    protected PersistenceButtonListener cancelSaveListener;

    protected JButton confirmButton;
    protected JButton cancelButton;

    public PersistencePanel(GuiGame guiGame) {
        this.guiGame = guiGame;
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setBackground(Color.GRAY);
        confirmLoadListener = new PersistenceButtonListener(guiGame, "load", true);
        confirmSaveListener = new PersistenceButtonListener(guiGame, "save", true);
        cancelLoadListener = new PersistenceButtonListener(guiGame, "load", false);
        cancelSaveListener = new PersistenceButtonListener(guiGame, "save", false);
    }

    public void centreString(String str, Graphics g, FontMetrics fm, int posY) {
        int width = fm.stringWidth(str);
        g.drawString(str, (Game.WIDTH - width) / 2, posY);
    }

    public abstract void createButtons();
}
