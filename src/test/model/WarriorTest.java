package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WarriorTest {
    Warrior w;

    @BeforeEach
    public void runBefore() {
        w = new Warrior();
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
