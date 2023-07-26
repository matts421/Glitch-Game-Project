package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    @Test
    public void checkMap(GameMap map1, GameMap map2) {
        assertEquals(map1.getName(), map2.getName());
        checkEnemies(map1.getEnemies(), map2.getEnemies());
        checkProjectiles(map1.getProjectiles(), map2.getProjectiles());
        checkBarriers(map1.getBarriers(), map2.getBarriers());
    }

    @Test
    public void checkPlayer(GameCharacter player, String fightClass, int health, int mana) {
        assertEquals(player.getMana(), mana);
        assertEquals(player.getHealth(), health);
        assertEquals(player.toString(), fightClass);
    }

    @Test
    public void checkEnemies(ArrayList<Enemy> enemies1, ArrayList<Enemy> enemies2) {
        assertEquals(enemies1.size(), enemies2.size());
        for (int i = 0; i < enemies1.size(); i++) {
            assertEquals(enemies1.get(i).getPosX(), enemies2.get(i).getPosX());
            assertEquals(enemies1.get(i).getPosY(), enemies2.get(i).getPosY());
        }
    }

    @Test
    public void checkProjectiles(ArrayList<Projectile> p1, ArrayList<Projectile> p2) {
        assertEquals(p1.size(), p2.size());
        for (int i = 0; i < p1.size(); i++) {
            assertEquals(p1.get(i).getPosX(), p2.get(i).getPosX());
            assertEquals(p1.get(i).getPosY(), p2.get(i).getPosY());
        }
    }

    @Test
    public void checkBarriers(ArrayList<Rectangle> b1, ArrayList<Rectangle> b2) {
        assertEquals(b1.size(), b2.size());
        for (int i = 0; i < b1.size(); i++) {
            assertEquals(b1.get(i).x, b2.get(i).x);
            assertEquals(b1.get(i).y, b2.get(i).y);
            assertEquals(b1.get(i).width, b2.get(i).width);
            assertEquals(b1.get(i).height, b2.get(i).height);
        }
    }

    @Test
    public void checkInventory(Inventory inventory, String itemName, int quantity) {
        for (Item i: inventory.getItems()) {
            if (i.getName().equals(itemName)) {
                assertEquals(inventory.getQuantity(i), quantity);
                break;
            }
        }
    }
}
