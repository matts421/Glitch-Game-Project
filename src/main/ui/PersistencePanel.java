package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;

public abstract class PersistencePanel extends JPanel {
    protected Game game;
    private boolean load;

    public PersistencePanel(Game g) {
        game = g;
        load = false;
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setBackground(Color.GRAY);
    }

    public boolean getLoad() {
        return load;
    }

    public void setLoad(boolean load) {
        this.load = load;
    }
}
