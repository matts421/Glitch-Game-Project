package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;

public abstract class MainGamePanel extends JPanel {
    protected Game game;

    public MainGamePanel(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setBackground(Color.GRAY);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void updateButtonListeners() {
        //
    }
}
