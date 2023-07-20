package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    Game game;
    GameCharacter gc;
    GameMap map;
    ArrayList<Rectangle> barriers;
    ArrayList<Enemy> enemies;
    ArrayList<Projectile> projectiles;
    Inventory inventory;
    Enemy e;

    @BeforeEach
    public void runBefore() {
        gc = new Warrior();
        barriers = new ArrayList<>();
        enemies = new ArrayList<>();
        e = new Enemy(0, 0);
        enemies.add(e);
        inventory = new Inventory();
        projectiles = new ArrayList<>();
        map = new GameMap(barriers, inventory, enemies, projectiles, "test");
        game = new Game(100, 100, gc, map);
    }

    @Test
    public void testConstructor() {
        assertEquals(map, game.getMap());
        assertEquals(gc, game.getPlayer());
        assertEquals(0, game.getTickCount());
        assertFalse(game.isEnded());
        assertFalse(game.isLevelOver());
    }

    @Test
    public void testTickCountUpdate() {
        game.tick();
        assertEquals(1, game.getTickCount());
    }

    @Test
    public void testTickUpdateMana() {
        gc.setMana(0);
        game.setTickCount(Game.MANA_TICKS - 1);
        game.tick();

        assertEquals(Game.MANA_BACK, gc.getMana());
        assertEquals(0, game.getTickCount());
    }

    @Test
    public void testTickUpdateManaFull() {
        gc.setMana(5);
        game.setTickCount(Game.MANA_TICKS - 1);
        game.tick();

        assertEquals(5, gc.getMana());
        assertEquals(0, game.getTickCount());
    }

    @Test
    public void testTickEndedHealth0() {
        gc.setHealth(0);
        game.tick();
        assertTrue(game.isEnded());
    }

    @Test
    public void testTickEndedXTooBig() {
        gc.setHealth(100);
        gc.setPosX(101);
        game.tick();
        assertTrue(game.isEnded());
    }

    @Test
    public void testTickEndedYTooBig() {
        gc.setHealth(100);
        gc.setPosY(101);
        game.tick();
        assertTrue(game.isEnded());
    }

    @Test
    public void testTickBeatLevel() {
        map.removeEnemy(e);
        game.tick();
        assertTrue(game.isLevelOver());
    }

    @Test
    public void testTickBeatLevel2() {
        game.setLevelOver(true);
        game.tick();
        assertTrue(game.isLevelOver());
    }

    @Test
    public void testTickMoveProjectiles() {
        Projectile p1 = new Projectile(gc);
        Projectile p2 = new Projectile(gc);
        map.addProjectile(p1);
        map.addProjectile(p2);

        game.tick();

        assertEquals(1, map.getProjectiles().get(0).getPosX());
        assertEquals(1, map.getProjectiles().get(1).getPosX());
    }

    @Test
    public void testNotOnPlatform() {
        assertFalse(game.onPlatform());
    }
    @Test
    public void testOnPlatform() {
        Rectangle barrier = new Rectangle(gc.getPosX(), gc.getPosY() + 1, 1, 1);
        map.getBarriers().add(barrier);
        assertTrue(game.onPlatform());
    }
    @Test
    public void testTickPlayerNotOnPlatform() {
        game.tick();
        assertEquals(22, gc.getPosY());
    }

    @Test
    public void testTickPlayerOnPlatform() {
        Rectangle barrier = new Rectangle(gc.getPosX(), gc.getPosY() + 1, 1, 1);
        map.getBarriers().add(barrier);
        game.tick();
        assertFalse(gc.isAirborne());
    }

    @Test
    public void testTickPlayerWallCollision() {
        Rectangle barrier = new Rectangle(gc.getPosX(), gc.getPosY(), 1, 2);
        map.getBarriers().add(barrier);
        game.tick();
        assertEquals(-1, gc.getPosX());
    }

    @Test
    public void testTickPlayerEnemyCollision() {
        gc.setPosX(0);
        gc.setPosY(0);
        gc.updateModel();
        Rectangle barrier = new Rectangle(gc.getPosX(), gc.getPosY() + 1, 1, 1);
        map.getBarriers().add(barrier);
        game.tick();

        assertEquals(-2, gc.getPosX());
        assertEquals(4, gc.getHealth());
    }

    @Test
    public void testTickPlayerItemCollision() {
        Rectangle barrier = new Rectangle(gc.getPosX(), gc.getPosY() + 1, 1, 1);
        Item item = new Item("test", TextColor.ANSI.WHITE, gc.getPosX(), gc.getPosY());
        map.getBarriers().add(barrier);
        map.getItems().addItem(item, 1);
        game.tick();

        assertTrue(gc.getInventory().getItems().contains(item));
        assertFalse(map.getItems().getItems().contains(item));
    }

    @Test
    public void testCycleCharacterClass() {
        game.cycleCharacterClass();
        assertEquals(TextColor.ANSI.CYAN, game.getPlayer().getColor());
        game.cycleCharacterClass();
        assertEquals(TextColor.ANSI.YELLOW, game.getPlayer().getColor());
        game.cycleCharacterClass();
        assertEquals(TextColor.ANSI.RED, game.getPlayer().getColor());
    }

    @Test
    public void testMaxJumpNoPlatformAbove() {
        assertEquals(Game.JUMP_HEIGHT, game.maxJump());
    }

    @Test
    public void testMaxJumpPlatform1Above() {
        Rectangle barrier = new Rectangle(gc.getPosX(), gc.getPosY() - 1, 1, 1);
        map.getBarriers().add(barrier);
        assertEquals(0, game.maxJump());
    }

    @Test
    public void testMaxJumpPlatform2Above() {
        Rectangle barrier = new Rectangle(gc.getPosX(), gc.getPosY() - 2, 1, 1);
        map.getBarriers().add(barrier);
        assertEquals(1, game.maxJump());
    }

    @Test
    public void testMaxJumpPlatform3Above() {
        Rectangle barrier = new Rectangle(gc.getPosX(), gc.getPosY() - 3, 1, 1);
        map.getBarriers().add(barrier);
        assertEquals(2, game.maxJump());
    }

    @Test
    public void testMaxJumpPlatform4Above() {
        Rectangle barrier = new Rectangle(gc.getPosX(), gc.getPosY() - 4, 1, 1);
        map.getBarriers().add(barrier);
        assertEquals(3, game.maxJump());
    }

    @Test
    public void testTickProjectileToWall() {
        Rectangle barrier = new Rectangle(gc.getPosX() + 1, gc.getPosY(), 1, 1);
        Projectile p1 = new Projectile(gc);
        map.getBarriers().add(barrier);
        map.getProjectiles().add(p1);

        game.tick();

        assertTrue(map.getProjectiles().isEmpty());
    }

    @Test
    public void testTickProjectileMissWall() {
        Rectangle barrier = new Rectangle(gc.getPosX() + 1, gc.getPosY() + 1, 1, 1);
        Projectile p1 = new Projectile(gc);
        map.getBarriers().add(barrier);
        map.getProjectiles().add(p1);

        game.tick();

        assertFalse(map.getProjectiles().isEmpty());
    }

    @Test
    public void testTickProjectileToEnemy() {
        gc.setPosX(-1);
        gc.setPosY(0);
        gc.updateModel();
        Projectile p1 = new Projectile(gc);
        map.getProjectiles().add(p1);

        game.tick();

        assertTrue(map.getProjectiles().isEmpty());
        assertTrue(map.getEnemies().isEmpty());
        assertFalse(map.getItems().getItems().isEmpty());
    }

    @Test
    public void testBuildMapOne() {
        GameMap map1 = game.buildMapOne();

        assertEquals("1", map1.getName());
        assertEquals(12, map1.getBarriers().size());
        assertEquals(2, map1.getEnemies().size());
        assertTrue(map1.getProjectiles().isEmpty());
        assertTrue(map1.getItems().getItems().isEmpty());
    }

    @Test
    public void testBuildMapTwo() {
        GameMap map1 = game.buildMapTwo();

        assertEquals("2", map1.getName());
        assertEquals(12, map1.getBarriers().size());
        assertEquals(3, map1.getEnemies().size());
        assertTrue(map1.getProjectiles().isEmpty());
        assertTrue(map1.getItems().getItems().isEmpty());
    }

    @Test
    public void testBuildMapThree() {
        GameMap map1 = game.buildMapThree();

        assertEquals("3", map1.getName());
        assertEquals(12, map1.getBarriers().size());
        assertEquals(3, map1.getEnemies().size());
        assertTrue(map1.getProjectiles().isEmpty());
        assertTrue(map1.getItems().getItems().isEmpty());
    }

    @Test
    public void testGetNextMap() {
        GameMap map1 = game.buildMapOne();

        game.setMap(map1);

        game.setMap(game.getNextMap());
        assertEquals("2", game.getMap().getName());

        game.setMap(game.getNextMap());
        assertEquals("3", game.getMap().getName());

        game.setMap(game.getNextMap());
        assertEquals("1", game.getMap().getName());
    }
}
