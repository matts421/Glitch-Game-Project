package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MageTest {
    Mage m;

    @BeforeEach
    public void runBefore() {
        Rectangle model = new Rectangle(GameCharacter.START_X, GameCharacter.START_Y, 1, 1);
        m = new Mage(Mage.MAX_HEALTH, Mage.MAX_MANA, 1, false,
                GameCharacter.START_X, GameCharacter.START_Y, new Inventory(), model);
    }

    @Test
    public void testConstructor() {
        assertEquals(5, m.getHealth());
        assertEquals(5, m.getMana());
        assertEquals(TextColor.ANSI.CYAN, m.getColor());
        assertEquals(1, m.getDirection());
        assertEquals(GameCharacter.START_X, m.getPosX());
        assertEquals(GameCharacter.START_Y, m.getPosY());

        assertTrue(m.getInventory().getItems().isEmpty());
        assertEquals(1, m.getModel().width);
        assertEquals(1, m.getModel().height);
        assertEquals(GameCharacter.START_X, m.getModel().x);
        assertEquals(GameCharacter.START_Y, m.getModel().y);
    }

    @Test
    public void testToString() {
        assertEquals("Mage", m.toString());
    }
}
