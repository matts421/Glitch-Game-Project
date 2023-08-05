package ui;

import model.Game;
import model.Item;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmButtonListener implements ActionListener {
    private PersistencePanel panel;

    public ConfirmButtonListener(PersistencePanel panel) {
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.setLoad(true);
    }
}
