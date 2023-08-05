package ui;

import model.Game;
import javax.swing.*;
import java.awt.*;

// Represents the panel shown when loading a game file.
public class LoadPanel extends PersistencePanel {

    // EFFECTS: instantiates the panel and creates confirm and cancel buttons.
    public LoadPanel(GuiGame g) {
        super(g);
        createButtons();

        add(confirmButton);
        add(cancelButton);
    }

    @Override
    public void createButtons() {
        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(confirmLoadListener);
        confirmButton.setFocusable(false);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(cancelLoadListener);
        cancelButton.setFocusable(false);
    }

    // MODIFIES: g
    // EFFECTS: Draws a load panel with a prompt asking the user to click confirm or cancel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawText(g);

        if (confirmButton.getParent() == null) {
            add(confirmButton);
        }

        if (cancelButton.getParent() == null) {
            add(cancelButton);
        }

        setButtonLocations();
    }

    // MODIFIES: this
    // EFFECTS: centres confirm and cancel buttons near the bottom of the screen.
    private void setButtonLocations() {
        confirmButton.setLocation(Game.WIDTH / 2 - 100 - confirmButton.getWidth(), Game.WIDTH / 2);
        cancelButton.setLocation(Game.WIDTH / 2 + 100, Game.WIDTH / 2);
    }

    // MODIFIES: g
    // EFFECTS: writes text asking the user to confirm their decision about loading the game.
    private void drawText(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString("Do you want to load from save file?", g, fm, Game.HEIGHT / 2);
        g.setColor(saved);
    }

}
