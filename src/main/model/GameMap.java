package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;
import java.util.ArrayList;

/*
    Represents the level stage of the game.
 */
public class GameMap extends HasModel implements Writable {
    private String name;
    private ArrayList<Rectangle> barriers;
    private Inventory items;
    private ArrayList<Enemy> enemies;
    private ArrayList<Projectile> projectiles;

    // EFFECTS: creates a map of the level stage including specified
    //          barriers, enemies, items, projectiles, and name.
    public GameMap(ArrayList<Rectangle> barriers,
                   Inventory items,
                   ArrayList<Enemy> enemies,
                   ArrayList<Projectile> projectiles, String name) {
        this.name = name;
        this.barriers = barriers;
        this.items = items;
        this.enemies = enemies;
        this.projectiles = projectiles;
    }

    // REQUIRES: items.getQuantity(item) >= quantity
    // MODIFIES: this
    // EFFECTS: remove a quantity of item from map
    public void removeItem(Item item, int quantity) {
        items.removeItem(item, quantity);
    }

    // MODIFIES: this
    // EFFECTS: adds projectile to projectiles
    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    // REQUIRES: projectiles.contains(projectile)
    // MODIFIES: this
    // EFFECTS: removes projectile from projectiles
    public void removeProjectile(Projectile projectile) {
        projectiles.remove(projectile);
    }

    // REQUIRES: enemies.contains(enemy)
    // MODIFIES: this
    // EFFECTS: removes enemy from enemies
    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public ArrayList<Rectangle> getBarriers() {
        return barriers;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public Inventory getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONArray barriersJson = new JSONArray();
        for (Rectangle barrier: barriers) {
            barriersJson.put(rectangleToJson(barrier));
        }

        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("items", items.toJson());
        json.put("barriers", barriersJson);
        json.put("enemies", enemiesToJson());
        json.put("projectiles", projectilesToJson());

        return json;
    }

    // EFFECTS: helper function to convert a list of projectiles to a JSONArray
    private JSONArray projectilesToJson() {
        JSONArray json = new JSONArray();
        for (Projectile projectile: projectiles) {
            json.put(projectile.toJson());
        }
        return json;
    }

    // EFFECTS: helper function to convert a list of enemies to a JSONArray
    private JSONArray enemiesToJson() {
        JSONArray json = new JSONArray();
        for (Enemy enemy: enemies) {
            json.put(enemy.toJson());
        }
        return json;
    }
}
