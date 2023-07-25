package model;

import com.googlecode.lanterna.TextColor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;

public class HasModel {

    public JSONArray createPosition(Integer posX, Integer posY) {
        JSONArray positions = new JSONArray();
        positions.put(posX);
        positions.put(posY);
        return positions;
    }

    public JSONObject rectangleToJson(Rectangle rect) {
        JSONObject json = new JSONObject();
        json.put("position", createPosition(rect.x, rect.y));
        json.put("width", rect.width);
        json.put("height", rect.height);
        return json;
    }

    public JSONObject rgbToJson(TextColor color) {
        JSONObject json = new JSONObject();
        Color c = color.toColor();
        json.put("red", c.getRed());
        json.put("green", c.getGreen());
        json.put("blue", c.getBlue());
        return json;
    }
}
