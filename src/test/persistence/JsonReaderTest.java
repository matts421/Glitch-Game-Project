package persistence;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.GameMap;
import model.Projectile;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// **NOTE** this class was inspired by Dr. Paul Carter's JsonSerializationDemo, and it takes elements from
//          JsonReaderTest
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyGame() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGame.json");
        try {
            Game game = reader.read();

            assertEquals(0, game.getTickCount());
            assertEquals(22, game.getMaxY());
            assertEquals(39, game.getMaxX());
            assertEquals("1", game.getMap().getName());
            assertEquals(5, game.getPlayer().getMaxMana());
            assertTrue(game.getMap().getProjectiles().isEmpty());
            assertTrue(game.getMap().getEnemies().isEmpty());
            assertTrue(game.getMap().getBarriers().isEmpty());
            assertTrue(game.getMap().getItems().getItems().isEmpty());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGameMage() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGameMage.json");
        try {
            Game game = reader.read();
            assertEquals(48, game.getTickCount());
            assertEquals(22 * Game.UP_SCALE, game.getMaxY());
            assertEquals(39 * Game.UP_SCALE, game.getMaxX());
            checkMap(game.getMap(), game.buildMapThree());
            assertEquals(5, game.getPlayer().getMaxMana());
            checkPlayer(game.getPlayer(), "Mage", 5, 0);
            checkInventory(game.getPlayer().getInventory(), "coin", 56);
            assertTrue(game.getMap().getItems().getItems().isEmpty());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGameRanger() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGameRanger.json");
        try {
            Game game = reader.read();
            assertEquals(48, game.getTickCount());
            assertEquals(22 * Game.UP_SCALE, game.getMaxY());
            assertEquals(39 * Game.UP_SCALE, game.getMaxX());
            GameMap map2 = game.buildMapThree();
            TextColor color = new TextColor.RGB(0,170, 170);
            Projectile p = new Projectile(33 * Game.UP_SCALE, 21 * Game.UP_SCALE, 1, color,
                    new Rectangle(33 * Game.UP_SCALE, 21 * Game.UP_SCALE,
                            Game.UP_SCALE, Game.UP_SCALE));
            map2.addProjectile(p);

            checkMap(game.getMap(), map2);
            assertEquals(5, game.getPlayer().getMaxMana());
            checkPlayer(game.getPlayer(), "Ranger", 5, 0);
            checkInventory(game.getPlayer().getInventory(), "coin", 56);
            assertTrue(game.getMap().getItems().getItems().isEmpty());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
