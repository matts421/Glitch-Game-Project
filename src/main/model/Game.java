package model;

import com.googlecode.lanterna.TextColor;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/*
    Represents the Glitch game itself. Encompasses all game logic and rules.
*/

public class Game implements Writable {
    public static final int TICKS_PER_SECOND = 10;
    public static final int JUMP_HEIGHT = 5;
    public static final int GRAVITY = 1;
    public static final int  MANA_TICKS = 50;
    public static final int  MANA_BACK = 1;
    private boolean levelOver;
    private int tickCount;
    private boolean ended;
    private final int maxX;
    private final int maxY;
    private GameCharacter player;
    private GameMap map;

    // EFFECTS: creates new game object with max x and y bounds, a character, and a map for the leve stage.
    //          The number of ticks starts at 0, and the game has not ended or completed the level stage.
    public Game(int maxX, int maxY, int tickCount, GameCharacter player, GameMap map) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.player = player;
        ended = false;
        levelOver = false;
        this.map = map;
        this.tickCount = tickCount;
    }

    // MODIFIES: this
    // EFFECTS: increments tickCount, updates mana and projectile movement, handles potential collisions,
    //          checks if game or level is completed and modifies ended and levelOver accordingly.
    public void tick() {
        tickCount++;

        updateMana();
        moveProjectiles();
        handleProjectileCollisions();
        handlePlayerCollisions();

        if (checkEnded()) {
            ended = true;
        } else if (checkBeatLevel()) {
            levelOver = true;
        }
    }

    // EFFECTS: returns true if player's health is 0, or player is out of bounds; false otherwise.
    private boolean checkEnded() {
        return (player.getHealth() == 0 || player.getPosX() > maxX || player.getPosY() > maxY);
    }

    // EFFECTS: returns true if map has no items and no enemies remaining; false otherwise.
    private boolean checkBeatLevel() {
        return (map.getItems().getItems().isEmpty() && map.getEnemies().isEmpty());
    }

    // MODIFIES: this
    // EFFECTS: every MANA_TICKS, regain MANA_BACK amount of mana, and reset tickCount to 0.
    private void updateMana() {
        if (tickCount == MANA_TICKS) {
            if (player.getMana() < 5) {
                player.gainMana(MANA_BACK);
            }
            tickCount = 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: update game to reflect any player collisions with walls, platforms, enemies, and items
    private void handlePlayerCollisions() {
        if (!onPlatform()) {
            player.fall();
        } else {
            player.setAirborne(false);
        }

        if (playerWallCollision()) {
            player.run(player.getDirection() * -1);
        }

        if (playerEnemyCollision()) {
            player.loseHealth(Enemy.DAMAGE);
            player.run(player.getDirection() * -2);
        }

        if (playerItemCollision()) {
            Item item = collidedItem();
            player.getInventory().addItem(item, map.getItems().getQuantity(item));
            map.removeItem(item, map.getItems().getQuantity(item));
        }
    }

    // MODIFIES: this
    // EFFECTS: changes current character class to next in cycle. Specifically:
    //          Warrior -> Mage, Mage -> Ranger, Ranger -> Warrior.
    public void cycleCharacterClass() {
        int currentHealth = player.getHealth();
        int currentMana = player.getMana();
        int currentX = player.getPosX();
        int currentY = player.getPosY();
        boolean currentAirborne = player.isAirborne();
        Inventory currentInventory = player.getInventory();
        Rectangle model = new Rectangle(GameCharacter.START_X, GameCharacter.START_Y, 1, 1);

        if (player.getColor() == TextColor.ANSI.RED) {
            player = new Mage(Mage.MAX_HEALTH, Mage.MAX_MANA, 1, false,
                    GameCharacter.START_X, GameCharacter.START_Y, new Inventory(), model);
        } else if (player.getColor() == TextColor.ANSI.CYAN) {
            player = new Ranger(Ranger.MAX_HEALTH, Ranger.MAX_MANA, 1, false,
                    GameCharacter.START_X, GameCharacter.START_Y, new Inventory(), model);
        } else {
            player = new Warrior(Warrior.MAX_HEALTH, Warrior.MAX_MANA, 1, false,
                    GameCharacter.START_X, GameCharacter.START_Y, new Inventory(), model);
        }

        player.setMana(currentMana);
        player.setHealth(currentHealth);
        player.setInventory(currentInventory);
        player.setAirborne(currentAirborne);
        player.setPosX(currentX);
        player.setPosY(currentY);
    }

    // EFFECTS: returns true if player is on a platform, false otherwise.
    public boolean onPlatform() {
        for (Rectangle barrier : map.getBarriers()) {
            if (barrier.y == player.getPosY() + 1
                    && barrier.x <= player.getPosX()
                    && (barrier.x + barrier.width) > player.getPosX()) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns the max jump height of player depending on platforms above them, capped at JUMP_HEIGHT.
    public int maxJump() {
        int bestDist = closestAbovePlatform();
        if (bestDist == -1 || bestDist > JUMP_HEIGHT) {
            return JUMP_HEIGHT;
        } else {
            return bestDist;
        }
    }

    // MODIFIES: this
    // EFFECTS: moves every projectile in the map
    private void moveProjectiles() {
        for (Projectile projectile: map.getProjectiles()) {
            projectile.move();
        }
    }

    // EFFECTS: returns true if the player's model intersects any barrier in the map
    private boolean playerWallCollision() {
        for (Rectangle barrier: map.getBarriers()) {
            if (barrier.intersects(player.getModel())) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns true if the player's model intersects any item in the map
    private boolean playerItemCollision() {
        for (Item item: map.getItems().getItems()) {
            if (player.getModel().intersects(item.getModel())) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns an item that a player is currently standing on. If not standing on any items in the map,
    //          returns null.
    public Item collidedItem() {
        Item item = null;
        for (Item i: map.getItems().getItems()) {
            if (player.getModel().intersects(i.getModel())) {
                item = i;
            }
        }
        return item;
    }

    // EFFECTS: returns the distance in pixels to the closest platform above the player. If there is no platform
    //          above, return -1.
    public int closestAbovePlatform() {
        int bestDist = -1;
        for (Rectangle barrier : map.getBarriers()) {
            if (barrier.y < player.getPosY()
                    && barrier.x <= player.getPosX()
                    && (barrier.x + barrier.width) > player.getPosX()) {
                int currentDist = (player.getPosY() - barrier.y - 1);
                if (bestDist == -1 || currentDist < bestDist) {
                    bestDist = currentDist;
                }
            }
        }
        return bestDist;
    }

    // MODIFIES: this
    // EFFECTS: removes all projectiles intersecting enemies or barriers on the map. If a projectile is intersecting
    //          an enemy, remove the enemy from the map too, and generate coins in its place.
    private void handleProjectileCollisions() {
        ArrayList<Projectile> projectilesToRemove = new ArrayList<>();
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

        for (Projectile projectile: map.getProjectiles()) {
            for (Rectangle barrier: map.getBarriers()) {
                if (projectile.getModel().intersects(barrier)) {
                    projectilesToRemove.add(projectile);
                    break;
                }
            }

            for (Enemy enemy: map.getEnemies()) {
                if (projectile.getModel().intersects(enemy.getModel())) {
                    projectilesToRemove.add(projectile);
                    enemiesToRemove.add(enemy);
                    generateMapCoins(enemy);
                }
            }

        }
        for (Projectile projectile: projectilesToRemove) {
            map.removeProjectile(projectile);
        }
        for (Enemy enemy: enemiesToRemove) {
            map.removeEnemy(enemy);
        }
    }

    // MODIFIES: this
    // EFFECTS: generates a random number of coins from 1 to 16 at the position of enemy and adds them to the map.
    private void generateMapCoins(Enemy enemy) {
        Item item = new Item("coin", TextColor.ANSI.GREEN,
                enemy.getPosX(), enemy.getPosY());
        Random random = new Random();
        map.getItems().addItem(item, 1 + random.nextInt(16));
    }

    // EFFECTS: returns true if the player's model is intersecting any of the map's enemies' models.
    //          False otherwise.
    private boolean playerEnemyCollision() {
        for (Enemy enemy: map.getEnemies()) {
            if (player.getModel().intersects(enemy.getModel())) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns the next game map after completing the current map. Specifically:
    //          Map1 -> Map2, Map2 -> Map3, Map3 -> Map1
    public GameMap getNextMap() {
        if (map.getName().equals("1")) {
            return buildMapTwo();
        } else if (map.getName().equals("2")) {
            return buildMapThree();
        } else {
            return buildMapOne();
        }
    }

    // EFFECTS: create and return the first level stage's map
    public GameMap buildMapOne() {
        ArrayList<Rectangle> barriers = new ArrayList<>();
        barriers.add(new Rectangle(0, 3, 10, 1));
        barriers.add(new Rectangle(6, 10, 3, 1));
        barriers.add(new Rectangle(10, 19, 3, 1));
        barriers.add(new Rectangle(16, 19, 3, 1));
        barriers.add(new Rectangle(19, 16, 5, 1));
        barriers.add(new Rectangle(16, 13, 3, 1));
        barriers.add(new Rectangle(21, 11, 15, 1));
        barriers.add(new Rectangle(29, 8, 4, 1));
        barriers.add(new Rectangle(23, 5, 5, 1));
        barriers.add(new Rectangle(12, 4, 9, 1));

        Enemy enemy1 = new Enemy(11, 18);
        Enemy enemy2 = new Enemy(3, 2);
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(enemy1);
        enemies.add(enemy2);

        barriers.add(new Rectangle(0, maxY, 9, 1));
        barriers.add(new Rectangle(15, maxY, 9, 1));

        return new GameMap(barriers, new Inventory(), enemies, new ArrayList<>(), "1");
    }

    // EFFECTS: create and return the second level stage's map
    public GameMap buildMapTwo() {
        ArrayList<Rectangle> barriers = new ArrayList<>();
        barriers.add(new Rectangle(0, 3, 10, 1));
        barriers.add(new Rectangle(6, 10, 3, 1));
        barriers.add(new Rectangle(10, 19, 3, 1));
        barriers.add(new Rectangle(16, 19, 3, 1));
        barriers.add(new Rectangle(19, 16, 5, 1));
        barriers.add(new Rectangle(16, 13, 3, 1));
        barriers.add(new Rectangle(21, 11, 15, 1));
        barriers.add(new Rectangle(29, 8, 4, 1));
        barriers.add(new Rectangle(23, 5, 5, 1));
        barriers.add(new Rectangle(12, 4, 9, 1));

        Enemy enemy1 = new Enemy(11, 18);
        Enemy enemy2 = new Enemy(3, 2);
        Enemy enemy4 = new Enemy(15, 3);

        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(enemy1);
        enemies.add(enemy2);
        enemies.add(enemy4);

        barriers.add(new Rectangle(0, maxY, 9, 1));
        barriers.add(new Rectangle(15, maxY, 9, 1));

        return new GameMap(barriers, new Inventory(), enemies, new ArrayList<>(), "2");
    }

    // EFFECTS: create and return the third level stage's map
    public GameMap buildMapThree() {
        ArrayList<Rectangle> barriers = new ArrayList<>();
        barriers.add(new Rectangle(0, 3, 10, 1));
        barriers.add(new Rectangle(6, 10, 3, 1));
        barriers.add(new Rectangle(10, 19, 3, 1));
        barriers.add(new Rectangle(16, 19, 3, 1));
        barriers.add(new Rectangle(19, 16, 5, 1));
        barriers.add(new Rectangle(16, 13, 3, 1));
        barriers.add(new Rectangle(21, 11, 15, 1));
        barriers.add(new Rectangle(29, 8, 4, 1));
        barriers.add(new Rectangle(23, 5, 5, 1));
        barriers.add(new Rectangle(12, 4, 9, 1));

        Enemy enemy1 = new Enemy(11, 18);
        Enemy enemy2 = new Enemy(3, 2);
        Enemy enemy4 = new Enemy(15, 3);

        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(enemy1);
        enemies.add(enemy2);
        enemies.add(enemy4);


        barriers.add(new Rectangle(0, maxY, 9, 1));
        barriers.add(new Rectangle(15, maxY, 9, 1));

        return new GameMap(barriers, new Inventory(), enemies, new ArrayList<>(), "3");
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("maxX", maxX);
        json.put("maxY", maxY);
        json.put("player", player.toJson());
        json.put("map", map.toJson());
        json.put("ticks", tickCount);
        return json;
    }

    public boolean isEnded() {
        return ended;
    }

    public boolean isLevelOver() {
        return levelOver;
    }

    public GameCharacter getPlayer() {
        return player;
    }

    public GameMap getMap() {
        return map;
    }

    public int getTickCount() {
        return tickCount;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public void setLevelOver(boolean over) {
        levelOver = over;
    }

    public void setTickCount(int tickCount) {
        this.tickCount = tickCount;
    }
}
