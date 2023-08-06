package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Represents the GUI of the game.
// NOTE: this class takes influence from SpaceInvaders
public class GuiGame extends JFrame {
    private static final String JSON_STORE = "./data/game.json";
    private static final int INTERVAL = 10;
    private Game game;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private MainGamePanel gp;
    private MainGamePanel ip;
    private JPanel mainPanel;
    private ScorePanel sp;
    private SavePanel savePanel;
    private LoadPanel loadPanel;
    private CardLayout cardLayout;
    private ArrayList<Integer> playerMovementKeyCodes;

    // EFFECTS: instantiates GUI, and sets up the game and its necessary panels.
    public GuiGame() {
        super("Glitch Game");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new LogHandler());
        setUndecorated(false);
        setUpGame();
        createPlayerMovementKeyCodes();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        ip = new InventoryPanel(game);
        gp = new GamePanel(game);
        savePanel = new SavePanel(this);
        loadPanel = new LoadPanel(this);

        buildMainPanel();

        sp = new ScorePanel(game);

        add(mainPanel);
        add(sp, BorderLayout.NORTH);
        addKeyListener(new KeyHandler());
        pack();
        setVisible(true);
        addTimer();
        cardLayout.show(mainPanel, "game");
    }

    // MODIFIES: this
    // EFFECTS: helper function to build the main panel in the card layout from
    //          the game, save, load, and inventory panels.
    private void buildMainPanel() {
        mainPanel.add(ip, "inventory");
        mainPanel.add(gp, "game");
        mainPanel.add(savePanel, "save");
        mainPanel.add(loadPanel, "load");
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates a brand new game state and instantiates the persistence objects.
    private void setUpGame() {
        Rectangle model = new Rectangle(GameCharacter.START_X, GameCharacter.START_Y,
                Game.UP_SCALE, Game.UP_SCALE);
        Warrior player = new Warrior(Warrior.MAX_HEALTH, Warrior.MAX_MANA, 1, false,
                GameCharacter.START_X, GameCharacter.START_Y, new Inventory(), model, Warrior.MAX_MANA);
        GameMap testMap = new GameMap(new ArrayList<>(), new Inventory(), new ArrayList<>(), new ArrayList<>(),
                "temp");
        game = new Game(Game.WIDTH, Game.HEIGHT, 0, player, testMap);
        GameMap newMap = game.buildMapOne();
        game.setMap(newMap);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: builds a list of player movement key codes
    private void createPlayerMovementKeyCodes() {
        playerMovementKeyCodes = new ArrayList<>();
        playerMovementKeyCodes.add(KeyEvent.VK_KP_LEFT);
        playerMovementKeyCodes.add(KeyEvent.VK_KP_RIGHT);
        playerMovementKeyCodes.add(KeyEvent.VK_UP);
        playerMovementKeyCodes.add(KeyEvent.VK_RIGHT);
        playerMovementKeyCodes.add(KeyEvent.VK_LEFT);
        playerMovementKeyCodes.add(KeyEvent.VK_SPACE);
    }

    // EFFECTS:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    // NOTE: this method was directly reproduced from SpaceInvaders
    private void addTimer() {
        Timer t = new Timer(INTERVAL, new ActionListener() {

            // MODIFIES: this
            // EFFECTS: progresses game state and repaints panels to match any changes
            @Override
            public void actionPerformed(ActionEvent ae) {
                tick();
                sp.update();
                ip.repaint();
                gp.repaint();
            }
        });

        t.start();
    }

    // MODIFIES: this
    // EFFECTS: General tick function for terminal game. Checks if the current level stage
    //          is completed and ticks the game clock.
    //
    //  **NOTE**: This code was inspired by Matzen Kotb's Lanterna Snake Console Game.
    public void tick() {

        if (game.isLevelOver()) {
            GameMap newMap = game.getNextMap();
            game.getPlayer().setPosX(0);
            game.getPlayer().setPosY(19 * Game.UP_SCALE);
            game.getPlayer().updateModel();
            game.setLevelOver(false);
            game.setMap(newMap);
        }

        game.tick();
    }

    // MODIFIES: this
    // EFFECTS: performs game actions defined by the input key code
    public void doAction(int keyCode) {
        if (playerMovementKeyCodes.contains(keyCode)) {
            handlePlayerActions(keyCode);
        } else if (keyCode == KeyEvent.VK_F1) {
            game.cycleCharacterClass();
        } else if (keyCode == KeyEvent.VK_F2 && !game.isEnded()) {
            cardLayout.show(mainPanel, "save");
        } else if (keyCode == KeyEvent.VK_F3 && !game.isEnded()) {
            cardLayout.show(mainPanel, "load");
        } else if (keyCode == KeyEvent.VK_E && !game.isEnded()) {
            handleInventory();
        } else if (keyCode == KeyEvent.VK_ESCAPE && game.isEnded()) {
            for (Event e: EventLog.getInstance()) {
                System.out.println(e);
            }
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: a helper function to handle all the key inputs that move the player or fire projectiles
    private void handlePlayerActions(int keyCode) {
        GameCharacter player = game.getPlayer();
        if (keyCode == KeyEvent.VK_KP_LEFT || keyCode == KeyEvent.VK_LEFT) {
            player.setDirection(-1);
            player.run();
        } else if (keyCode == KeyEvent.VK_KP_RIGHT || keyCode == KeyEvent.VK_RIGHT) {
            player.setDirection(1);
            player.run();
        } else if (keyCode == KeyEvent.VK_UP) {
            player.jump(game.maxJump());
        } else if (keyCode == KeyEvent.VK_SPACE) {
            Projectile projectile = player.basicAttack();
            if (projectile != null) {
                game.getMap().addProjectile(projectile);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: opens or closes the inventory panel depending on whether it is closed.
    private void handleInventory() {
        if (game.isInventoryOpen()) {
            game.setInventoryOpen(false);
            cardLayout.show(mainPanel, "game");
        } else {
            game.setInventoryOpen(true);
            cardLayout.show(mainPanel, "inventory");
        }
    }


    // Represents a key handler to capture key presses for the game
    private class KeyHandler extends KeyAdapter {

        // MODIFIES: this
        // EFFECTS: performs actions outlined in the doAction method depending on inputted key code.
        @Override
        public void keyPressed(KeyEvent e) {
            doAction(e.getKeyCode());
        }
    }

    // Represents a log handler to print the game logs upon closing the JFrame
    private class LogHandler extends WindowAdapter {

        // EFFECTS: prints out the game's event log to the console when the game window is closed.
        @Override
        public void windowClosing(WindowEvent windowEvent) {
            for (Event e: EventLog.getInstance()) {
                System.out.println(e);
            }
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads game from file
    public void loadGame() {
        try {
            game = jsonReader.read();
            gp.setGame(game);
            sp.setGame(game);
            ip.setGame(game);
            ip.updateButtonListeners();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the game to file
    public void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            ip.updateButtonListeners();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}
