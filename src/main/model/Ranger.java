package model;

import com.googlecode.lanterna.TextColor;
import persistence.Writable;

import java.awt.*;

/*
    Subclass of GameCharacter.
    Specifies a Ranger's unique color, maximum health and mana, and string representation
 */
public class Ranger extends GameCharacter implements Writable {
    private static final TextColor TEXT_COLOR = TextColor.ANSI.YELLOW;
    public static final int MAX_HEALTH = 5;
    public static final int MAX_MANA = 5;

    // EFFECTS: creates ranger with health, mana, and color specified by
    //          MAX_HEALTH, MAX_MANA, and TEXT_COLOR
    public Ranger(int health, int mana, int direction, boolean airborne,
                   int posX, int posY, Inventory inventory, Rectangle model, int maxMana) {
        super(health, mana, direction, airborne, posX, posY, inventory, model, TEXT_COLOR, maxMana);
    }

    // EFFECTS: returns Ranger for string representation
    @Override
    public String toString() {
        return "Ranger";
    }
}
