package model;

import com.googlecode.lanterna.TextColor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;

// Parent class for any custom object in model that has an in-game model (hit-box)
public class HasModel {

    // EFFECTS: returns an array that packages the inputted x and y position together
    public JSONArray createPosition(Integer posX, Integer posY) {
        JSONArray positions = new JSONArray();
        positions.put(posX);
        positions.put(posY);
        return positions;
    }

    // EFFECTS: returns a JSON object that packages x, y, height, and width of the input rectangle
    public JSONObject rectangleToJson(Rectangle rect) {
        JSONObject json = new JSONObject();
        json.put("position", createPosition(rect.x, rect.y));
        json.put("width", rect.width);
        json.put("height", rect.height);
        return json;
    }

    // EFFECTS: returns a JSON object that represents red, green, and blue channels of the input color.
    public JSONObject rgbToJson(TextColor color) {
        JSONObject json = new JSONObject();
        Color c = color.toColor();
        json.put("red", c.getRed());
        json.put("green", c.getGreen());
        json.put("blue", c.getBlue());
        return json;
    }
}
