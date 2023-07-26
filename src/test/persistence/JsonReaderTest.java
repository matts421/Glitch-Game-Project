package persistence;

import model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

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
    void testReaderGeneralGame() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGame.json");
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
}
