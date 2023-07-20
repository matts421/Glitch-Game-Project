package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MageTest {
    Mage m;

    @BeforeEach
    public void runBefore() {
        m = new Mage();
    }

    @Test
    public void testConstructor() {
        assertEquals(5, m.getHealth());
        assertEquals(5, m.getMana());
        assertEquals(TextColor.ANSI.CYAN, m.getColor());
        assertEquals(1, m.getDirection());
        assertEquals(0, m.getPosX());
        assertEquals(21, m.getPosY());

        assertTrue(m.getInventory().getItems().isEmpty());
        assertEquals(1, m.getModel().width);
        assertEquals(1, m.getModel().height);
        assertEquals(0, m.getModel().x);
        assertEquals(21, m.getModel().y);
    }

    @Test
    public void testToString() {
        assertEquals("Mage", m.toString());
    }
}
