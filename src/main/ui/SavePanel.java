package ui;

import model.Game;
import persistence.JsonWriter;

import java.io.FileNotFoundException;

public class SavePanel extends PersistencePanel {
    String store;
    JsonWriter jsonWriter;

    public SavePanel(Game g, JsonWriter jsonWriter, String store) {
        super(g);
        this.jsonWriter = jsonWriter;
        this.store = store;
    }

    // EFFECTS: saves the game to file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + store);
        }
    }

}
