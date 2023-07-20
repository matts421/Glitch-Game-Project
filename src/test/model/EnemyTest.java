package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnemyTest {
    Enemy e;
    @BeforeEach
    public void runBefore() {
        e = new Enemy(0, 0);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, e.getPosX());
        assertEquals(0, e.getPosY());
        assertEquals(TextColor.ANSI.MAGENTA, e.getColor());

        assertEquals(0, e.getModel().x);
        assertEquals(0, e.getModel().y);
        assertEquals(1, e.getModel().width);
        assertEquals(1, e.getModel().height);
    }
}
