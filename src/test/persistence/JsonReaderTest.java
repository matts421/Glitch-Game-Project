package persistence;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.GameMap;
import model.Projectile;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Game game = reader.read();
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
            assertEquals(22, game.getMaxY());
            assertEquals(39, game.getMaxX());
            checkMap(game.getMap(), game.buildMapThree());
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
            assertEquals(22, game.getMaxY());
            assertEquals(39, game.getMaxX());
            GameMap map2 = game.buildMapThree();
            TextColor color = new TextColor.RGB(0,170, 170);
            Projectile p = new Projectile(33, 21, 1, color,
                    new Rectangle(33, 21, 1, 1));
            map2.addProjectile(p);

            checkMap(game.getMap(), map2);
            checkPlayer(game.getPlayer(), "Ranger", 5, 0);
            checkInventory(game.getPlayer().getInventory(), "coin", 56);
            assertTrue(game.getMap().getItems().getItems().isEmpty());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
