package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static com.googlecode.lanterna.input.KeyType.*;

/*
    This class handles all the UI aspects of Glitch interacts with the game class to update the game state and
    reflect such on the terminal window.

**NOTE**: Much of this class was inspired by Matzen Kotb's Lanterna Snake Console Game. Specific methods have been
          denoted with this disclaimer as such.
 */
public class TerminalGame {
    private static final String JSON_STORE = "./data/game.json";
    private WindowBasedTextGUI endGui;
    private Game game;
    private Screen screen;
    private int maxX;
    private int maxY;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: this
    // EFFECTS: starts game cycle. Initializes player's class to default Warrior, and begins game on Map1.
    //          Sets up terminal window screen and begins tick cycle.
    //
    //  **NOTE**: This code was inspired by Matzen Kotb's Lanterna Snake Console Game.
    public void start() throws IOException, InterruptedException {
        Rectangle model = new Rectangle(GameCharacter.START_X, GameCharacter.START_Y, 1, 1);
        Warrior player = new Warrior(Warrior.MAX_HEALTH, Warrior.MAX_MANA, 1, false,
                GameCharacter.START_X, GameCharacter.START_Y, new Inventory(), model);

        screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();

        TerminalSize terminalSize = screen.getTerminalSize();
        maxX = (terminalSize.getColumns() - 1) / 2;
        maxY = terminalSize.getRows() - 2;

        GameMap testMap = new GameMap(new ArrayList<>(), new Inventory(), new ArrayList<>(), new ArrayList<>(),
                "temp");
        game = new Game(maxX, maxY, 0, player, testMap);
        GameMap newMap = game.buildMapOne();

        game.setMap(newMap);

        beginTicks();
    }

    // MODIFIES: this
    // EFFECTS: if game is not ended, indefinitely tick's game cycle. Exit when game is ended.
    //
    // **NOTE**: This code was inspired by Matzen Kotb's Lanterna Snake Console Game.
    public void beginTicks() throws IOException, InterruptedException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        while (!game.isEnded()) {
            tick();
            Thread.sleep(1000L / Game.TICKS_PER_SECOND);
        }
        System.exit(0);
    }

    // MODIFIES: this
    // EFFECTS: General tick function for terminal game. Handle's keyboard input, checks if the current level stage
    //          is completed, ticks the game clock, and renders the game information onto the terminal window.
    //
    //  **NOTE**: This code was inspired by Matzen Kotb's Lanterna Snake Console Game.
    public void tick() throws IOException {

        handleInput();

        if (game.isLevelOver()) {
            GameMap newMap = game.getNextMap();
            game.getPlayer().setPosX(0);
            game.getPlayer().setPosY(21);
            game.getPlayer().updateModel();
            game.setLevelOver(false);
            game.setMap(newMap);
        }

        game.tick();

        screen.setCursorPosition(new TerminalPosition(0, 0));
        screen.clear();
        render();
        screen.refresh();

        screen.setCursorPosition(new TerminalPosition(screen.getTerminalSize().getColumns() - 1, 0));
    }

    // MODIFIES: this
    // EFFECTS: polls input for keystrokes and passes information to action helper function.
    //
    //  **NOTE**: This code was inspired by Matzen Kotb's Lanterna Snake Console Game.
    public void handleInput() throws IOException {
        KeyStroke stroke = screen.pollInput();

        if (stroke == null) {
            return;
        }

        if (stroke.getCharacter() != null) {
            return;
        }

        doAction(stroke.getKeyType());
    }

    // MODIFIES: this
    // EFFECTS: Executes the following commands depending on the key type. Specifically:
    //          ArrowRight: moves player right
    //          ArrowLeft:  moves player left
    //          ArrowUp:    makes the player jump
    //          Escape:     makes the player use a basic attack
    //          F1:         cycles the player's class
    //
    //  **NOTE**: This code was inspired by Matzen Kotb's Lanterna Snake Console Game.
    public void doAction(KeyType type) {
        GameCharacter player = game.getPlayer();

        if (type == ArrowLeft) {
            player.setDirection(-1);
            player.run();
        } else if (type == ArrowRight) {
            player.setDirection(1);
            player.run();
        } else if (type == ArrowUp) {
            player.jump(game.maxJump());
        } else if (type == Escape) {
            Projectile projectile = player.basicAttack();
            if (projectile != null) {
                game.getMap().addProjectile(projectile);
            }
        } else if (type == F1) {
            game.cycleCharacterClass();
        } else if (type == F2) {
            saveGame();
        } else if (type == F3) {
            loadGame();
        }
    }

    // MODIFIES: this
    // EFFECTS: draws end screen if game is over.
    //          Otherwise, draws healthbar, manabar, level, class, player, barriers, projectiles, enemies, and items
    //          on to the terminal window.
    //
    //  **NOTE**: This code was inspired by Matzen Kotb's Lanterna Snake Console Game.
    private void render() {
        if (game.isEnded()) {
            if (endGui == null) {
                drawEndScreen();
            }

            return;
        }

        drawHealthBar();
        drawManaBar();
        drawLevel();
        drawClass();
        drawPlayer();
        drawBarriers();
        drawProjectiles();
        drawEnemies();
        drawItems();
    }

    // MODIFIES: this
    // EFFECTS: generates the end screen and displays the total number of coins collected by the player
    //
    //  **NOTE**: This code was inspired by Matzen Kotb's Lanterna Snake Console Game.
    private void drawEndScreen() {
        endGui = new MultiWindowTextGUI(screen);
        GameCharacter player = game.getPlayer();
        Item coin = new Item("coin", TextColor.ANSI.WHITE, 0, 0);

        new MessageDialogBuilder()
                .setTitle("Game over!")
                .setText("You finished with " + player.getInventory().getQuantity(coin) + " coins!")
                .addButton(MessageDialogButton.Close)
                .build()
                .showDialog(endGui);
    }

    // MODIFIES: this
    // EFFECTS: draws current level at the top of the screen in white
    private void drawLevel() {
        drawPosition(15, 0, TextColor.ANSI.WHITE, "Level: " + game.getMap().getName());
    }

    // MODIFIES: this
    // EFFECTS: draws current class at the top of the screen in the class' color
    private void drawClass() {
        drawPosition(25, 0, game.getPlayer().getColor(), "Class: " + game.getPlayer().toString());
    }

    // MODIFIES: this
    // EFFECTS: draws on the screen the player at their current position in their class' color, represented by
    //          a smile emoticon.
    private void drawPlayer() {
        GameCharacter player = game.getPlayer();
        drawPosition(player.getPosX(), player.getPosY(), player.getColor(), 'o');
    }

    // MODIFIES: this
    // EFFECTS: draws all the map's items on the screen in their respective positions, represented by
    //          a dollar sign emoticon.
    private void drawItems() {
        for (Item item : game.getMap().getItems().getItems()) {
            int quantity = game.getMap().getItems().getQuantity(item);
            drawPosition(item.getPosX(), item.getPosY(), item.getColor(), '$');
            drawPosition(item.getPosX(), item.getPosY() - 1, TextColor.ANSI.DEFAULT,
                    Integer.toString(quantity));
        }
    }

    // MODIFIES: this
    // EFFECTS: draws all the map's enemies on the screen in their respective positions, represented by
    //          the enemy's RENDER_MODEL
    private void drawEnemies() {
        for (Enemy enemy : game.getMap().getEnemies()) {
            drawPosition(enemy.getPosX(), enemy.getPosY(), enemy.getColor(), Enemy.RENDER_MODEL);
        }
    }

    // MODIFIES: this
    // EFFECTS: draws all the map's projectiles on the screen in their respective positions, represented by
    //          an arrow in their respective directions.
    private void drawProjectiles() {
        for (Projectile projectile : game.getMap().getProjectiles()) {
            char projectileImage;

            if (projectile.getDirection() == 1) {
                projectileImage = Projectile.RIGHT_PROJECTILE;
            } else {
                projectileImage = Projectile.LEFT_PROJECTILE;
            }

            drawPosition(projectile.getPosX(), projectile.getPosY(),
                    projectile.getColor(), projectileImage);
        }
    }

    // MODIFIES: this
    // EFFECTS: draws a health bar at the top of the screen depicting the player's remaining lives
    private void drawHealthBar() {
        for (int i = 0; i < game.getPlayer().getHealth(); i++) {
            drawPosition(i, 0, TextColor.ANSI.RED, "<3");
        }
    }

    // MODIFIES: this
    // EFFECTS: draws a mana bar at the top of the screen depicting the player's remaining mana
    private void drawManaBar() {
        for (int i = 6; i < game.getPlayer().getMana() + 6; i++) {
            drawPosition(i, 0, TextColor.ANSI.BLUE, '*');
        }
    }

    // MODIFIES: this
    // EFFECTS: draws all the map's barriers on the screen at their respective positions, represented by
    //          a block character.
    private void drawBarriers() {
        for (Rectangle barrier : game.getMap().getBarriers()) {
            for (int i = barrier.x; i < barrier.width + barrier.x; i++) {
                for (int j = barrier.y; j < barrier.height + barrier.y; j++) {
                    drawPosition(i, j, TextColor.ANSI.WHITE, 'T');
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: draws string to terminal window at position (xpos, ypos) in color.
    //
    //  **NOTE**: This code was inspired by Matzen Kotb's Lanterna Snake Console Game.
    private void drawPosition(int xpos, int ypos, TextColor color, char c) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);
        text.putString(xpos * 2, ypos + 1, String.valueOf(c));
    }

    // MODIFIES: this
    // EFFECTS: draws string to terminal window at position (xpos, ypos) in color.
    //
    //  **NOTE**: This code was inspired by Matzen Kotb's Lanterna Snake Console Game.
    private void drawPosition(int xpos, int ypos, TextColor color, String s) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);
        text.putString(xpos * 2, ypos + 1, s);
    }

    // EFFECTS: saves the game to file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads game from file
    private void loadGame() {
        try {
            game = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
