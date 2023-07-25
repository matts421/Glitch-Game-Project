package model;

import com.googlecode.lanterna.TextColor;
import persistence.Writable;

import java.awt.*;

/*
    Subclass of GameCharacter.
    Specifies a Mage's unique color, maximum health and mana, and string representation
 */
public class Mage extends GameCharacter implements Writable {
    private static final TextColor TEXT_COLOR = TextColor.ANSI.CYAN;
    public static final int MAX_HEALTH = 5;
    public static final int MAX_MANA = 5;

    // EFFECTS: creates mage with health, mana, and color specified by
    //          MAX_HEALTH, MAX_MANA, and TEXT_COLOR
    public Mage(int health, int mana, int direction, boolean airborne,
                   int posX, int posY, Inventory inventory, Rectangle model) {
        super(health, mana, direction, airborne, posX, posY, inventory, model, TEXT_COLOR);
    }

    // EFFECTS: returns Mage for string representation
    @Override
    public String toString() {
        return "Mage";
    }
}
