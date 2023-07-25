package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WarriorTest {
    Warrior w;

    @BeforeEach
    public void runBefore() {
        Rectangle model = new Rectangle(GameCharacter.START_X, GameCharacter.START_Y, 1, 1);
        w = new Warrior(Warrior.MAX_HEALTH, Warrior.MAX_MANA, 1, false,
                GameCharacter.START_X, GameCharacter.START_Y, new Inventory(), model);
    }

    @Test
    public void testConstructor() {
        assertEquals(5, w.getHealth());
        assertEquals(5, w.getMana());
        assertEquals(TextColor.ANSI.RED, w.getColor());
        assertEquals(1, w.getDirection());
        assertEquals(0, w.getPosX());
        assertEquals(21, w.getPosY());

        assertTrue(w.getInventory().getItems().isEmpty());
        assertEquals(1, w.getModel().width);
        assertEquals(1, w.getModel().height);
        assertEquals(0, w.getModel().x);
        assertEquals(21, w.getModel().y);
    }

    @Test
    public void testToString() {
        assertEquals("Warrior", w.toString());
    }
}
