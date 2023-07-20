package model;

import java.awt.*;
import java.util.ArrayList;

/*
    Represents the level stage of the game.
 */
public class GameMap {
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

}
