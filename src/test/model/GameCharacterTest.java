package model;

import com.googlecode.lanterna.TextColor;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

// Tests for GameCharacter class
public class GameCharacterTest {
    GameCharacter gc;

    @BeforeEach
    public void runBefore() {
        Rectangle model = new Rectangle(GameCharacter.START_X, GameCharacter.START_Y, 1, 1);
        Inventory inventory = new Inventory();
        gc = new GameCharacter(0, 0, 1, false,
                GameCharacter.START_X, GameCharacter.START_Y, inventory, model, TextColor.ANSI.WHITE, 0);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, gc.getHealth());
        assertEquals(0, gc.getMana());
        assertEquals(TextColor.ANSI.WHITE, gc.getColor());
        assertEquals(1, gc.getDirection());
        assertEquals(GameCharacter.START_X, gc.getPosX());
        assertEquals(GameCharacter.START_Y, gc.getPosY());
        assertEquals(0, gc.getMaxMana());

        assertTrue(gc.getInventory().getItems().isEmpty());
        assertEquals(1, gc.getModel().width);
        assertEquals(1, gc.getModel().height);
        assertEquals(GameCharacter.START_X, gc.getModel().x);
        assertEquals(GameCharacter.START_Y, gc.getModel().y);
    }

    @Test
    public void testBasicAttackNoMana() {
        assertNull(gc.basicAttack());
    }

    @Test
    public void testBasicAttackOnce() {
        Projectile p1 = new Projectile(gc);

        gc.gainMana(GameCharacter.MANA_COST);
        Projectile p2 = gc.basicAttack();

        assertEquals(p2.getModel(), p1.getModel());
        assertEquals(p2.getColor(), p1.getColor());
        assertEquals(p2.getDirection(), p1.getDirection());
        assertEquals(p2.getPosX(), p1.getPosX());
        assertEquals(p2.getPosY(), p1.getPosY());
    }

    @Test
    public void testBasicAttackTwice() {
        Projectile p1 = new Projectile(gc);

        gc.gainMana(GameCharacter.MANA_COST * 3);
        Projectile p2 = gc.basicAttack();
        Projectile p3 = gc.basicAttack();

        assertEquals(p2.getModel(), p1.getModel());
        assertEquals(p2.getColor(), p1.getColor());
        assertEquals(p2.getDirection(), p1.getDirection());
        assertEquals(p2.getPosX(), p1.getPosX());
        assertEquals(p2.getPosY(), p1.getPosY());

        assertEquals(p3.getModel(), p1.getModel());
        assertEquals(p3.getColor(), p1.getColor());
        assertEquals(p3.getDirection(), p1.getDirection());
        assertEquals(p3.getPosX(), p1.getPosX());
        assertEquals(p3.getPosY(), p1.getPosY());
    }

    @Test
    public void testRunNoArgumentsRight() {
        gc.run();

        assertEquals(Game.UP_SCALE, gc.getPosX());
        assertEquals(Game.UP_SCALE, gc.getModel().x);
    }

    @Test
    public void testRunNoArgumentsLeft() {
        gc.setDirection(-1);
        gc.run();

        assertEquals(-1 * Game.UP_SCALE, gc.getPosX());
        assertEquals(-1 * Game.UP_SCALE, gc.getModel().x);
    }

    @Test
    public void testRunDirectionLeft() {
        gc.run(-1);

        assertEquals(-1, gc.getPosX());
        assertEquals(-1, gc.getModel().x);
    }

    @Test
    public void testRunDirectionRight() {
        gc.run(1);

        assertEquals(1, gc.getPosX());
        assertEquals(1, gc.getModel().x);
    }

    @Test
    public void testJumpAirborne() {
        gc.setPosX(0);
        gc.setPosY(0);
        gc.updateModel();

        gc.setAirborne(true);

        gc.jump(1);
        assertEquals(0, gc.getPosY());
        assertEquals(0, gc.getModel().y);
        assertTrue(gc.isAirborne());
    }

    @Test
    public void testJumpNotAirborne() {
        gc.setPosX(0);
        gc.setPosY(0);
        gc.updateModel();

        gc.jump(1);
        assertEquals(-1, gc.getPosY());
        assertEquals(-1, gc.getModel().y);
        assertTrue(gc.isAirborne());
    }

    @Test
    public void testJumpBig() {
        gc.setPosX(0);
        gc.setPosY(0);
        gc.updateModel();

        gc.jump(100);
        assertEquals(-100, gc.getPosY());
        assertEquals(-100, gc.getModel().y);
        assertTrue(gc.isAirborne());
    }


    @Test
    public void testFallOnce() {
        gc.setPosX(0);
        gc.setPosY(0);
        gc.updateModel();

        gc.fall();
        assertEquals(Game.GRAVITY, gc.getPosY());
        assertEquals(Game.GRAVITY, gc.getModel().y);
    }

    @Test
    public void testFallManyTimes() {
        gc.setPosX(0);
        gc.setPosY(0);
        gc.updateModel();

        gc.fall();
        gc.fall();
        gc.fall();

        assertEquals(Game.GRAVITY * 3, gc.getPosY());
        assertEquals(Game.GRAVITY * 3, gc.getModel().y);
    }


    @Test
    public void testUpdateModel() {
        gc.setPosX(1);
        gc.setPosY(1);
        gc.updateModel();

        gc.updateModel();

        assertEquals(1, gc.getModel().x);
        assertEquals(1, gc.getModel().y);
    }

    @Test
    public void testGainOneMana() {
        gc.gainMana(1);
        assertEquals(1, gc.getMana());
    }

    @Test
    public void testGainManyMana() {
        gc.gainMana(100);
        assertEquals(100, gc.getMana());
    }

    @Test
    public void testLoseOneHealth() {
        gc.setHealth(100);

        gc.loseHealth(1);

        assertEquals(99, gc.getHealth());
    }

    @Test
    public void testLoseManyHealth() {
        gc.setHealth(100);

        gc.loseHealth(1);
        gc.loseHealth(10);
        gc.loseHealth(20);

        assertEquals(69, gc.getHealth());
    }

    @Test
    public void testSetMana() {
        gc.setMana(100);

        assertEquals(100, gc.getMana());
    }

    @Test
    public void testSetMaxMana() {
        gc.setMaxMana(100);
        assertEquals(100, gc.getMaxMana());
    }

    @Test
    public void testSetInventory() {
        Inventory testInventory = new Inventory();
        Item testItem = new Item("test", TextColor.ANSI.WHITE, 0, 0);
        testInventory.addItem(testItem, 1);

        gc.setInventory(testInventory);

        assertEquals(testInventory, gc.getInventory());
    }

    @Test
    public void testLoseItem() {
        Item i = new Item("coin", TextColor.ANSI.WHITE, 0, 0);
        gc.getInventory().addItem(i, 10);
        gc.loseItem(i, 1);
        assertEquals(gc.getInventory().getQuantity(i), 9);
    }

    @Test
    public void testToJsonGeneral() {
        JSONObject jsonObject = gc.toJson();
        assertEquals(0, jsonObject.getInt("health"));
        assertEquals(0, jsonObject.getInt("mana"));
        assertEquals(1, jsonObject.getInt("direction"));
        assertFalse(jsonObject.getBoolean("airborne"));
    }

    @Test
    public void testToJsonWarrior() {
        Inventory inventory = new Inventory();
        Rectangle model = new Rectangle(0, 0, 1, 1);
        Warrior w = new Warrior(0, 0, 1, false, 0, 0,
                inventory, model, 100);

        JSONObject jsonObject = w.toJson();
        assertEquals("Warrior", jsonObject.getString("name"));
    }

    @Test
    public void testToJsonMage() {
        Inventory inventory = new Inventory();
        Rectangle model = new Rectangle(0, 0, 1, 1);
        Mage m = new Mage(0, 0, 1, false, 0, 0,
                inventory, model, 100);

        JSONObject jsonObject = m.toJson();
        assertEquals("Mage", jsonObject.getString("name"));
    }

    @Test
    public void testToJsonRanger() {
        Inventory inventory = new Inventory();
        Rectangle model = new Rectangle(0, 0, 1, 1);
        Ranger r = new Ranger(0, 0, 1, false, 0, 0,
                inventory, model, 100);

        JSONObject jsonObject = r.toJson();
        assertEquals("Ranger", jsonObject.getString("name"));
    }
}
