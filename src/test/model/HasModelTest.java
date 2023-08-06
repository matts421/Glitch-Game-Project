package model;

import com.googlecode.lanterna.TextColor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Tests for HasModel class
public class HasModelTest {

    HasModel hm;

    @BeforeEach
    public void runBefore() {
        hm = new HasModel();
    }

    @Test
    public void testCreatePosition() {
        int posX = 1;
        int posY = 1;
        JSONArray jsonArray = hm.createPosition(posX, posY);

        assertEquals(jsonArray.getInt(0), posX);
        assertEquals(jsonArray.getInt(1), posY);
    }

    @Test
    public void testRectangleToJson() {
        Rectangle rect = new Rectangle(0, 0, 1, 1);
        JSONObject jsonObject = hm.rectangleToJson(rect);

        JSONArray positions = jsonObject.getJSONArray("position");

        assertEquals(jsonObject.getInt("width"), 1);
        assertEquals(jsonObject.getInt("height"), 1);
        assertEquals(positions.getInt(0), 0);
        assertEquals(positions.getInt(1), 0);
    }

    @Test
    public void testRgbToJsonRGB() {
        TextColor.RGB color = new TextColor.RGB(0,0,0);
        JSONObject jsonObject = hm.rgbToJson(color);

        assertEquals(jsonObject.getInt("red"), 0);
        assertEquals(jsonObject.getInt("green"), 0);
        assertEquals(jsonObject.getInt("blue"), 0);
    }

    @Test
    public void testRgbToJsonANSI() {
        JSONObject jsonObject = hm.rgbToJson(TextColor.ANSI.BLACK);

        assertEquals(jsonObject.getInt("red"), 0);
        assertEquals(jsonObject.getInt("green"), 0);
        assertEquals(jsonObject.getInt("blue"), 0);
    }
}
