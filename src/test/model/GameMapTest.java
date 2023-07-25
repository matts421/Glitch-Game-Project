package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameMapTest {
    GameMap map;
    ArrayList<Rectangle> barriers;
    ArrayList<Enemy> enemies;
    ArrayList<Projectile> projectiles;
    Inventory inventory;
    Enemy e;




    @BeforeEach
    public void runBefore() {
         barriers = new ArrayList<>();
         enemies = new ArrayList<>();
         e = new Enemy(0,0);
         enemies.add(e);
         projectiles = new ArrayList<>();
         inventory = new Inventory();

        map = new GameMap(barriers, inventory, enemies, projectiles, "test");
    }

    @Test
    public void testConstructor() {
        assertEquals("test", map.getName());
        assertEquals(enemies, map.getEnemies());
        assertEquals(projectiles, map.getProjectiles());
        assertEquals(barriers, map.getBarriers());
        assertEquals(inventory, map.getItems());
    }

    @Test
    public void testRemoveOneItem() {
        Item item1 = new Item("1", TextColor.ANSI.WHITE, 0, 0);
        map.getItems().addItem(item1, 10);

        map.removeItem(item1, 1);

        assertEquals(9, map.getItems().getQuantity(item1));
    }

    @Test
    public void testRemoveAllItem() {
        Item item1 = new Item("1", TextColor.ANSI.WHITE, 0, 0);
        map.getItems().addItem(item1, 10);

        map.removeItem(item1, 10);

        assertFalse(map.getItems().getItems().contains(item1));
    }

    @Test
    public void testAddOneProjectile() {
        Inventory inventory = new Inventory();
        Rectangle model = new Rectangle(0, 0, 1, 1);

        GameCharacter gc = new GameCharacter(0, 0, 1, false, 0, 0,
                inventory, model, TextColor.ANSI.WHITE);
        gc.setPosY(0);
        gc.updateModel();

        Projectile p1 = new Projectile(gc);
        map.addProjectile(p1);

        assertTrue(map.getProjectiles().contains(p1));
    }

    @Test
    public void testRemoveOneProjectile() {
        Inventory inventory = new Inventory();
        Rectangle model = new Rectangle(0, 0, 1, 1);

        GameCharacter gc = new GameCharacter(0, 0, 1, false, 0, 0,
                inventory, model, TextColor.ANSI.WHITE);
        gc.setPosY(0);
        gc.updateModel();

        Projectile p1 = new Projectile(gc);
        map.addProjectile(p1);
        map.removeProjectile(p1);

        assertFalse(map.getProjectiles().contains(p1));

    }

    @Test
    public void testRemoveOneEnemy() {
        map.removeEnemy(e);

        assertFalse(map.getEnemies().contains(e));
    }

}
