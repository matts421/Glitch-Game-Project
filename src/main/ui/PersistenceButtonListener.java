package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersistenceButtonListener implements ActionListener {
    private GuiGame guiGame;
    private String action;
    private boolean accept;

    public PersistenceButtonListener(GuiGame guiGame, String action, boolean accept) {
        this.guiGame = guiGame;
        this.action = action;
        this.accept = accept;
    }

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
