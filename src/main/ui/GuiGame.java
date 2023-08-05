package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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

    public GuiGame() {
        super("Glitch Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setUpGame();
        createPlayerMovementKeyCodes();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        ip = new InventoryPanel(game);
        gp = new GamePanel(game);
        savePanel = new SavePanel(this);
        loadPanel = new LoadPanel(this);

        mainPanel.add(ip, "inventory");
        mainPanel.add(gp, "game");
        mainPanel.add(savePanel, "save");
        mainPanel.add(loadPanel, "load");

        sp = new ScorePanel(game);

        add(mainPanel);
        add(sp, BorderLayout.NORTH);
        addKeyListener(new KeyHandler());
        pack();
        setVisible(true);
        addTimer();
        cardLayout.show(mainPanel, "game");
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void setUpGame() {
        Rectangle model = new Rectangle(GameCharacter.START_X, GameCharacter.START_Y,
                1 * Game.UP_SCALE, 1 * Game.UP_SCALE);
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

    private void createPlayerMovementKeyCodes() {
        playerMovementKeyCodes = new ArrayList<>();
        playerMovementKeyCodes.add(KeyEvent.VK_KP_LEFT);
        playerMovementKeyCodes.add(KeyEvent.VK_KP_RIGHT);
        playerMovementKeyCodes.add(KeyEvent.VK_UP);
        playerMovementKeyCodes.add(KeyEvent.VK_RIGHT);
        playerMovementKeyCodes.add(KeyEvent.VK_LEFT);
        playerMovementKeyCodes.add(KeyEvent.VK_SPACE);
    }

    // Set up timer
    // modifies: none
    // effects:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        Timer t = new Timer(INTERVAL, new ActionListener() {
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
    // EFFECTS: General tick function for terminal game. Handle's keyboard input, checks if the current level stage
    //          is completed, ticks the game clock, and renders the game information onto the terminal window.
    //
    //  **NOTE**: This code was inspired by Matzen Kotb's Lanterna Snake Console Game.
    public void tick() {

        if (game.isLevelOver()) {
            GameMap newMap = game.getNextMap();
            game.getPlayer().setPosX(0 * Game.UP_SCALE);
            game.getPlayer().setPosY(19 * Game.UP_SCALE);
            game.getPlayer().updateModel();
            game.setLevelOver(false);
            game.setMap(newMap);
        }

        game.tick();
    }

    public void doAction(int keyCode) {
        if (playerMovementKeyCodes.contains(keyCode)) {
            handlePlayerActions(keyCode);
        }
        if (keyCode == KeyEvent.VK_F1) {
            game.cycleCharacterClass();
        } else if (keyCode == KeyEvent.VK_F2) {
//            saveGame();
            cardLayout.show(mainPanel, "save");
        } else if (keyCode == KeyEvent.VK_F3) {
//            loadGame();
            cardLayout.show(mainPanel, "load");
        } else if (keyCode == KeyEvent.VK_E) {
            handleInventory();
        } else if (keyCode == KeyEvent.VK_ESCAPE && game.isEnded()) {
            System.exit(0);
        }
    }

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

    private void handleInventory() {
        if (game.isInventoryOpen()) {
            game.setInventoryOpen(false);
            cardLayout.show(mainPanel, "game");
        } else {
            game.setInventoryOpen(true);
            cardLayout.show(mainPanel, "inventory");
        }
    }


    /*
     * A key handler to respond to key events
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            doAction(e.getKeyCode());
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
