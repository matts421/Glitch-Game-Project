package model;

import com.googlecode.lanterna.TextColor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnemyTest {
    Enemy e;
    Enemy e2;
    @BeforeEach
    public void runBefore() {
        e = new Enemy(0, 0);
        e2 = new Enemy(0, 0, new Rectangle(0, 0, 1, 1));
    }

    @Test
    public void testConstructor1() {
        assertEquals(0, e.getPosX());
        assertEquals(0, e.getPosY());
        assertEquals(TextColor.ANSI.MAGENTA, e.getColor());

        assertEquals(0, e.getModel().x);
        assertEquals(0, e.getModel().y);
        assertEquals(1, e.getModel().width);
        assertEquals(1, e.getModel().height);
    }

    @Test
    public void testConstructor2() {
        TextColor.RGB color = new TextColor.RGB(255,0, 255);
        assertEquals(0, e2.getPosX());
        assertEquals(0, e2.getPosY());
        assertEquals(color, e2.getColor());

        assertEquals(0, e2.getModel().x);
        assertEquals(0, e2.getModel().y);
        assertEquals(1, e2.getModel().width);
        assertEquals(1, e2.getModel().height);
    }

    @Test
    public void testToJson() {
        JSONObject jsonObject = e.toJson();
        JSONArray position = jsonObject.getJSONArray("position");
        assertEquals(position.get(0), 0);
        assertEquals(position.get(1), 0);
    }
}
