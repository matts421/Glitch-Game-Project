package model;

import com.googlecode.lanterna.TextColor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

    @Test
    public void testConstructor2() {
        Item item2 = new Item("test", TextColor.ANSI.WHITE, 0, 0,
                new Rectangle(0, 0, 1,1));
        assertEquals(0, item2.getPosX());
        assertEquals(0, item2.getPosY());
        assertEquals(TextColor.ANSI.WHITE, item2.getColor());

        assertEquals(0, item2.getModel().x);
        assertEquals(0, item2.getModel().y);
        assertEquals(1, item2.getModel().width);
        assertEquals(1, item2.getModel().height);

    }

    @Test
    public void testEqualsSameObject() {
        assertEquals(item, item);
    }

    @Test
    public void testEqualsNotSameClass() {
        Enemy e = new Enemy(0, 0);

        assertFalse(item.equals(e));
    }

    @Test
    public void testEqualsSameName() {
        Item item2 = new Item("test", TextColor.ANSI.RED, 1, 1);

        assertEquals(item, item2);
    }

    @Test
    public void testEqualsNotSameName() {
        Item item2 = new Item("not test", TextColor.ANSI.WHITE, 0, 0);

        assertFalse(item.equals(item2));
    }

    @Test
    public void testToJson() {
        JSONObject jsonObject = item.toJson();

        JSONArray position = jsonObject.getJSONArray("position");
        JSONObject model = jsonObject.getJSONObject("model");
        JSONObject color = jsonObject.getJSONObject("color");

        assertEquals(jsonObject.getString("name"), item.getName());
        assertEquals(position.get(0), 0);
        assertEquals(position.get(1), 0);

    }

    @Test
    public void testHashCode() {
        assertEquals(Objects.hash(item.getName()), item.hashCode());
    }

}
