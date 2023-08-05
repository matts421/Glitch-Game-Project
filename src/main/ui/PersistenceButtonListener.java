package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a button listener for the load and save game buttons
public class PersistenceButtonListener implements ActionListener {
    private GuiGame guiGame;
    private String action;
    private boolean accept;

    // EFFECTS: instantiates the listener with the gui, an action string to dictate load or save,
    //          and an accept to dictate confirm or cancel.
    public PersistenceButtonListener(GuiGame guiGame, String action, boolean accept) {
        this.guiGame = guiGame;
        this.action = action;
        this.accept = accept;
    }

    // MODIFIES: this
    // EFFECTS: depending on the save or load action, as well as the confirm or cancel button clicked,
    //          does or does not save or load a game file. Always closes persistence panel after the respective action.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (accept) {
            if (action.equals("load")) {
                guiGame.loadGame();
            } else if (action.equals("save")) {
                guiGame.saveGame();
            }
        }
        guiGame.getCardLayout().show(guiGame.getMainPanel(), "game");
    }
}
