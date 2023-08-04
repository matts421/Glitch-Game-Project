package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RangerTest {
    Ranger r;

    @BeforeEach
    public void runBefore() {
        Rectangle model = new Rectangle(GameCharacter.START_X, GameCharacter.START_Y, 1, 1);
        r = new Ranger(Ranger.MAX_HEALTH, Ranger.MAX_MANA, 1, false,
                GameCharacter.START_X, GameCharacter.START_Y, new Inventory(), model);
    }

    @Test
    public void testConstructor() {
        assertEquals(5, r.getHealth());
        assertEquals(5, r.getMana());
        assertEquals(TextColor.ANSI.YELLOW, r.getColor());
        assertEquals(1, r.getDirection());
        assertEquals(GameCharacter.START_X, r.getPosX());
        assertEquals(GameCharacter.START_Y, r.getPosY());

        assertTrue(r.getInventory().getItems().isEmpty());
        assertEquals(1, r.getModel().width);
        assertEquals(1, r.getModel().height);
        assertEquals(GameCharacter.START_X, r.getModel().x);
        assertEquals(GameCharacter.START_Y, r.getModel().y);
    }

    @Test
    public void testToString() {
        assertEquals("Ranger", r.toString());
    }
}
