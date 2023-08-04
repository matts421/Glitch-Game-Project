package model;

import com.googlecode.lanterna.TextColor;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;

/*
    The parent class for the three subclasses of Mage, Warrior, and Ranger.
    Represents the game player's character.
 */
public class GameCharacter extends HasModel implements Writable {
    public static final int MANA_COST = 1;
    public static final int START_X = 0 * Game.UP_SCALE;
//    public static final int START_Y = 21 * Game.UP_SCALE - Game.UP_SCALE;
    public static final int START_Y = 19 * Game.UP_SCALE;
    private TextColor color;
    private int health;
    private int mana;
    private int direction;
    private int posX;
    private int posY;
    private Rectangle model;
    private boolean airborne;
    private Inventory inventory;

    // EFFECTS: creates character with given health, mana, and color.
    //          Starts with empty inventory, facing right, and position
    //          in the bottom left corner of the screen. Hit-box is a single
    //          pixel centered at the x/y position.
    public GameCharacter(int health, int mana, int direction, boolean airborne,
                         int posX, int posY, Inventory inventory, Rectangle model, TextColor color) {
        this.health = health;
        this.mana = mana;
        this.color = color;
        this.inventory = inventory;
        this.direction = direction;
        this.airborne = airborne;

        this.posX = posX;
        this.posY = posY;
        this.model = model;
    }

    // MODIFIES: this
    // EFFECTS: if mana >= MANA_COST, returns a new projectile and lowers mana by MANA_COST
    //          Otherwise, do nothing and return null.
    public Projectile basicAttack() {
        if (mana >= MANA_COST) {
            mana -= MANA_COST;
            return new Projectile(this);
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: changes posX by amount of this.direction and updates model to new posX.
    public void run() {
        posX += (direction * Game.UP_SCALE);
        model.x = posX;
    }

    // REQUIRES: direction is -1 or 1
    // MODIFIES: this
    // EFFECTS: changes posX by amount of direction and updates model to new posX.
    public void run(int direction) {
        posX += direction;
        model.x = posX;
    }

    // MODIFIES: this
    // EFFECTS: if not airborne, decreases posY by height, updates model to new posY,
    //          and sets airborne to true. Otherwise, do nothing.
    public void jump(int height) {
        if (!airborne) {
            posY -= height;
            model.y = posY;
            airborne = true;
        }
    }

    // MODIFIES: this
    // EFFECTS: increases PosY by GRAVITY and updates model to reflect this change.
    public void fall() {
        posY += Game.GRAVITY;
        model.y = posY;
    }

    // MODIFIES: this
    // EFFECTS: updates model to match current positioning of player
    public void updateModel() {
        model.x = posX;
        model.y = posY;
    }

    // MODIFIES: this
    // EFFECTS: adds amount to mana
    public void gainMana(int amount) {
        mana += amount;
    }

    // REQUIRES: health >= amount
    // MODIFIES: this
    // EFFECTS: removes amount from health
    public void loseHealth(int amount) {
        health -= amount;
    }

    public boolean isAirborne() {
        return airborne;
    }

    public void setDirection(int dir) {
        direction = dir;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAirborne(boolean airborne) {
        this.airborne = airborne;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Rectangle getModel() {
        return model;
    }

    public int getDirection() {
        return direction;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public TextColor getColor() {
        return color;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        String name;

        if (color == TextColor.ANSI.RED) {
            name = "Warrior";
        } else if (color == TextColor.ANSI.CYAN) {
            name = "Mage";
        } else {
            name = "Ranger";
        }

        json.put("name", name);
        json.put("health", health);
        json.put("mana", mana);
        json.put("direction", direction);
        json.put("airborne", airborne);
        json.put("inventory", inventory.toJson());
        json.put("position", createPosition(posX, posY));
        json.put("model", rectangleToJson(model));
        return json;
    }
}
