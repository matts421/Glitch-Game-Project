package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
//    private static final String INVADERS_TXT = "Invaders shot down: ";
//    private static final String MISSILES_TXT = "Missiles remaining: ";
    private static final int LBL_WIDTH = 200;
    private static final int LBL_HEIGHT = 30;
    private Game game;
    private JLabel healthLabel;
    private JLabel manaLabel;
    private JLabel levelLabel;
    private JLabel classLabel;

    // Constructs a score panel
    // effects: sets the background colour and draws the initial labels;
    //          updates this with the game whose score is to be displayed
    public ScorePanel(Game g) {
        game = g;
        setBackground(new Color(180, 180, 180));

        healthLabel = new JLabel("❤".repeat(game.getPlayer().getHealth()));
        healthLabel.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        healthLabel.setForeground(new Color(255, 0,0));

        manaLabel = new JLabel("★".repeat(game.getPlayer().getMana()));
        manaLabel.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        manaLabel.setForeground(new Color(0,0,255));

        levelLabel = new JLabel("Level: " + game.getMap().getName());
        levelLabel.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));

        classLabel = new JLabel("Class: " + game.getPlayer().toString());
        classLabel.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        classLabel.setForeground(game.getPlayer().getColor().toColor());

        add(healthLabel);
        add(Box.createHorizontalStrut(10));
        add(manaLabel);
        add(levelLabel);
        add(classLabel);
    }

    // Updates the score panel
    // modifies: this
    // effects:  updates number of invaders shot and number of missiles
    //           remaining to reflect current state of game
    public void update() {
        healthLabel.setText("❤".repeat(game.getPlayer().getHealth()));
        manaLabel.setText("★".repeat(game.getPlayer().getMana()));
        levelLabel.setText("Level: " + game.getMap().getName());
        classLabel.setText("Class: " + game.getPlayer().toString());
        classLabel.setForeground(game.getPlayer().getColor().toColor());

        repaint();
    }
}
