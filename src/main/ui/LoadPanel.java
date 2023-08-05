package ui;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.Item;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LoadPanel extends PersistencePanel {

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

    private void setButtonLocations() {
        confirmButton.setLocation(Game.WIDTH / 2 - 100 - confirmButton.getWidth(), Game.WIDTH / 2);
        cancelButton.setLocation(Game.WIDTH / 2 + 100, Game.WIDTH / 2);
    }

    private void drawText(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString("Do you want to load from save file?", g, fm, Game.HEIGHT / 2);
        g.setColor(saved);
    }

}
