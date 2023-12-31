package model;

import com.googlecode.lanterna.TextColor;
import persistence.Writable;

import java.awt.*;

/*
    Subclass of GameCharacter.
    Specifies a Warrior's unique color, maximum health and mana, and string representation
 */
public class Warrior extends GameCharacter implements Writable {
    private static final TextColor TEXT_COLOR = TextColor.ANSI.RED;
    public static final int MAX_HEALTH = 5;
    public static final int MAX_MANA = 5;

    // EFFECTS: creates warrior with health, mana, and color specified by
    //          MAX_HEALTH, MAX_MANA, and TEXT_COLOR
    public Warrior(int health, int mana, int direction, boolean airborne,
                   int posX, int posY, Inventory inventory, Rectangle model, int maxMana) {
        super(health, mana, direction, airborne, posX, posY, inventory, model, TEXT_COLOR, maxMana);
    }

    // EFFECTS: returns Warrior for string representation
    @Override
    public String toString() {
        return "Warrior";
    }

}
