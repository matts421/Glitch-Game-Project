package model;

import com.googlecode.lanterna.TextColor;

/*
    Subclass of GameCharacter.
    Specifies a Mage's unique color, maximum health and mana, and string representation
 */
public class Mage extends GameCharacter {
    private static final TextColor TEXT_COLOR = TextColor.ANSI.CYAN;
    private static final int MAX_HEALTH = 5;
    private static final int MAX_MANA = 5;

    // EFFECTS: creates mage with health, mana, and color specified by
    //          MAX_HEALTH, MAX_MANA, and TEXT_COLOR
    public Mage() {
        super(MAX_HEALTH, MAX_MANA, TEXT_COLOR);
    }

    // EFFECTS: returns Mage for string representation
    @Override
    public String toString() {
        return "Mage";
    }
}
