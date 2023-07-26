package persistence;

import com.googlecode.lanterna.TextColor;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.fail;

// **NOTE** this class was inspired by Dr. Paul Carter's JsonSerializationDemo and it takes elements from
//          JsonWriterTest
public class JsonWriterTest extends JsonTest {
    Game game;
    Warrior w;
    GameMap dummyMap;
    @BeforeEach
    public void runBefore() {
        Inventory inventory = new Inventory();
        Rectangle model = new Rectangle(0,0,1,1);
        w = new Warrior(0, 0, 1, false, 0, 0,
                inventory, model);
        dummyMap = new GameMap(new ArrayList<>(), new Inventory(), new ArrayList<>(), new ArrayList<>(),
                "test");
        game = new Game(100, 100, 0, w, dummyMap);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGame() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGame.json");
            game = reader.read();
            checkMap(game.getMap(), dummyMap);
            checkPlayer(game.getPlayer(), "Warrior", 0, 0);
            checkInventory(game.getPlayer().getInventory(), "coin", 0);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGame() {
        try {
            Item coin = new Item("coin", TextColor.ANSI.WHITE, 0, 0,
                    new Rectangle(0,0,1,1));
            GameMap newMap = game.buildMapThree();
            newMap.addProjectile(new Projectile(w));
            game.setMap(newMap);
            game.getPlayer().getInventory().addItem(coin, 10);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGame.json");
            game = reader.read();

            checkMap(game.getMap(), newMap);
            checkPlayer(game.getPlayer(), "Warrior", 0, 0);
            checkInventory(game.getPlayer().getInventory(), "coin", 10);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
