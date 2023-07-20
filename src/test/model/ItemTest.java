package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {
    Item item;

    @BeforeEach
    public void runBefore() {
        item = new Item("test", TextColor.ANSI.WHITE, 0, 0);
    }

    @Test
    public void testConstructor() {
        assertEquals("test", item.getName());
        assertEquals(0, item.getPosX());
        assertEquals(0, item.getPosY());
        assertEquals(TextColor.ANSI.WHITE, item.getColor());

        assertEquals(0, item.getModel().x);
        assertEquals(0, item.getModel().y);
        assertEquals(1, item.getModel().width);
        assertEquals(1, item.getModel().height);
    }
}
