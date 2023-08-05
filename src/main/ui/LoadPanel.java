package ui;

import model.Game;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LoadPanel extends PersistencePanel {
    String store;
    JsonReader jsonReader;

    public LoadPanel(Game g, JsonReader jsonReader, String store) {
        super(g);
        this.jsonReader = jsonReader;
        this.store = store;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawConfirmButton(g);
//        drawDeclineButton(g);
    }

    private void drawConfirmButton(Graphics g) {
        Color savedCol = g.getColor();
        JButton loadButton = new JButton("Confirm load");
        //itemButton.setLocation(posX, posY + CELL_SIZE);
        loadButton.addActionListener(new ConfirmButtonListener(this));
        loadButton.setSize(120, 30);
        loadButton.setFocusable(false);
        add(loadButton);
    }

    // MODIFIES: this
    // EFFECTS: loads game from file
    private void loadGame() {
        try {
            game = jsonReader.read();
//            gp.setGame(game);
//            sp.setGame(game);
//            ip.setGame(game);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + store);
        }
    }

}
