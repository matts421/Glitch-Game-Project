package persistence;

import com.googlecode.lanterna.TextColor;
import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Game read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses game from JSON object and returns it
    private Game parseGame(JSONObject jsonObject) {
        int maxX = jsonObject.getInt("maxX");
        int maxY = jsonObject.getInt("maxY");
        int tickCount = jsonObject.getInt("ticks");
        GameCharacter gc;

        JSONObject playerJson = jsonObject.getJSONObject("player");

        if (playerJson.getString("name").equals("Warrior")) {
            gc = parseWarrior(playerJson);
        } else if (playerJson.getString("name").equals("Mage")) {
            gc = parseMage(playerJson);
        } else {
            gc = parseRanger(playerJson);
        }

        Game game = new Game(maxX, maxY, tickCount, gc, parseMap(jsonObject.getJSONObject("map")));
        return game;
    }

    private GameMap parseMap(JSONObject json) {
        ArrayList<Enemy> enemyList = new ArrayList<>();
        ArrayList<Projectile> projectileList = new ArrayList<>();
        ArrayList<Rectangle> barrierList = new ArrayList<>();

        Inventory items = parseInventory(json.getJSONObject("items"));
        JSONArray projectiles = json.getJSONArray("projectiles");
        JSONArray enemies = json.getJSONArray("enemies");
        JSONArray barriers = json.getJSONArray("barriers");

        for (Object enemy: enemies) {
            JSONObject enemyJson = (JSONObject) enemy;
            Enemy realEnemy = parseEnemy(enemyJson);
            enemyList.add(realEnemy);
        }

        for (Object projectile: projectiles) {
            JSONObject projectileJson = (JSONObject) projectile;
            Projectile realProjectile = parseProjectile(projectileJson);
            projectileList.add(realProjectile);
        }

        for (Object barrier: barriers) {
            JSONObject barrierJson = (JSONObject) barrier;
            Rectangle realBarrier = parseModel(barrierJson);
            barrierList.add(realBarrier);
        }

        return new GameMap(barrierList, items, enemyList, projectileList, json.getString("name"));
    }

    private Projectile parseProjectile(JSONObject jsonObject) {
        TextColor.RGB color = parseColor(jsonObject.getJSONObject("color"));
        int direction = jsonObject.getInt("direction");
        int posX = jsonObject.getJSONArray("position").getInt(0);
        int posY = jsonObject.getJSONArray("position").getInt(1);
        Rectangle model = parseModel(jsonObject.getJSONObject("model"));

        return new Projectile(posX, posY, direction, color, model);
    }

    private Enemy parseEnemy(JSONObject jsonObject) {
        int posX = jsonObject.getJSONArray("position").getInt(0);
        int posY = jsonObject.getJSONArray("position").getInt(1);
        Rectangle model = parseModel(jsonObject.getJSONObject("model"));

        return new Enemy(posX, posY, model);
    }

    private Warrior parseWarrior(JSONObject json) {
        int health = json.getInt("health");
        int mana = json.getInt("mana");
        int direction = json.getInt("direction");
        boolean airborne = json.getBoolean("airborne");
        int posX = json.getJSONArray("position").getInt(0);
        int posY = json.getJSONArray("position").getInt(1);
        Rectangle model = parseModel(json.getJSONObject("model"));
        Inventory inventory = parseInventory(json.getJSONObject("inventory"));

        return new Warrior(health, mana, direction, airborne, posX, posY, inventory, model);
    }

    private Mage parseMage(JSONObject json) {
        int health = json.getInt("health");
        int mana = json.getInt("mana");
        int direction = json.getInt("direction");
        boolean airborne = json.getBoolean("airborne");
        int posX = json.getJSONArray("position").getInt(0);
        int posY = json.getJSONArray("position").getInt(1);
        Rectangle model = parseModel(json.getJSONObject("model"));
        Inventory inventory = parseInventory(json.getJSONObject("inventory"));

        return new Mage(health, mana, direction, airborne, posX, posY, inventory, model);
    }

    private Ranger parseRanger(JSONObject json) {
        int health = json.getInt("health");
        int mana = json.getInt("mana");
        int direction = json.getInt("direction");
        boolean airborne = json.getBoolean("airborne");
        int posX = json.getJSONArray("position").getInt(0);
        int posY = json.getJSONArray("position").getInt(1);
        Rectangle model = parseModel(json.getJSONObject("model"));
        Inventory inventory = parseInventory(json.getJSONObject("inventory"));

        return new Ranger(health, mana, direction, airborne, posX, posY, inventory, model);
    }

    private Rectangle parseModel(JSONObject json) {
        int x = json.getJSONArray("position").getInt(0);
        int y = json.getJSONArray("position").getInt(1);
        int width = json.getInt("width");
        int height = json.getInt("height");
        return new Rectangle(x, y, width, height);
    }

    private Inventory parseInventory(JSONObject json) {
        Inventory inventory = new Inventory();
        for (String itemName: json.keySet()) {
            Object itemObject = json.get(itemName);
            JSONObject itemBundleJson = (JSONObject) itemObject;
            JSONObject itemJson = itemBundleJson.getJSONObject(itemName);
            int quantity = itemBundleJson.getInt("quantity");

            inventory.addItem(parseItem(itemJson), quantity);
        }
        return inventory;
    }

    private Item parseItem(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int x = jsonObject.getJSONArray("position").getInt(0);
        int y = jsonObject.getJSONArray("position").getInt(1);
        Rectangle model = parseModel(jsonObject.getJSONObject("model"));
        TextColor.RGB color = parseColor(jsonObject.getJSONObject("color"));
        return new Item(name, color, x, y, model);
    }

    private TextColor.RGB parseColor(JSONObject jsonObject) {
        int red = jsonObject.getInt("red");
        int green = jsonObject.getInt("green");
        int blue = jsonObject.getInt("blue");
        return new TextColor.RGB(red, green, blue);
    }

}
