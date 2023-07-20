package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RangerTest {
    Ranger r;

    @BeforeEach
    public void runBefore() {
        r = new Ranger();
    }

    @Test
    public void testConstructor() {
        assertEquals(5, r.getHealth());
        assertEquals(5, r.getMana());
        assertEquals(TextColor.ANSI.YELLOW, r.getColor());
        assertEquals(1, r.getDirection());
        assertEquals(0, r.getPosX());
        assertEquals(21, r.getPosY());

        assertTrue(r.getInventory().getItems().isEmpty());
        assertEquals(1, r.getModel().width);
        assertEquals(1, r.getModel().height);
        assertEquals(0, r.getModel().x);
        assertEquals(21, r.getModel().y);
    }

    @Test
    public void testToString() {
        assertEquals("Ranger", r.toString());
    }
}
