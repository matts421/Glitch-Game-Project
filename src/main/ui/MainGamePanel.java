package ui;

import model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Represents a general game panel that encapsulates some of the shared functionality between
// GamePanel and InventoryPanel.
// NOTE: See README.MD for proper art attribution of the assets.
public abstract class MainGamePanel extends JPanel {
    protected Game game;
    private static final String BACKGROUND_PATH = "./data/background.png";
    private static final String SPRITES_PATH  = "./data/sprites.png";
    private static final String ENEMY_SPRITES_PATH = "./data/enemy_sprites.png";
    private static final int SPRITE_SCALE = 16;
    protected Image background;
    protected Image platformImage;
    protected Image coinImage;
    protected List<Image> enemyImages;

    // EFFECTS: instantiates the panel given the game state, and creates a panel with
    //          the dimensions of the game, and a gray background.
    public MainGamePanel(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setBackground(Color.GRAY);
        loadImages();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    // MODIFIES: this
    // EFFECTS: loads background, coin, and platform sprites from data
    public void loadImages() {
        try {
            background = ImageIO.read(new File(BACKGROUND_PATH));
            background = background.getScaledInstance(Game.WIDTH, Game.HEIGHT, Image.SCALE_DEFAULT);

            BufferedImage spriteImage = ImageIO.read(new File(SPRITES_PATH));

            platformImage = spriteImage.getSubimage(10 * SPRITE_SCALE, 5 * SPRITE_SCALE,
                    SPRITE_SCALE, SPRITE_SCALE);
            platformImage = platformImage.getScaledInstance(Game.UP_SCALE, Game.UP_SCALE, Image.SCALE_DEFAULT);
            coinImage = spriteImage.getSubimage(19 * SPRITE_SCALE, SPRITE_SCALE, SPRITE_SCALE, SPRITE_SCALE);
            coinImage = coinImage.getScaledInstance(Game.UP_SCALE, Game.UP_SCALE, Image.SCALE_DEFAULT);
            loadEnemySprites();

        } catch (IOException ioe) {
            // do nothing
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a scaled list of the different enemy sprites by loading them from data.
    private void loadEnemySprites() {
        try {
            BufferedImage spriteImage = ImageIO.read(new File(ENEMY_SPRITES_PATH));
            enemyImages = new ArrayList<>();
            List<Image> scaledEnemies = new ArrayList<>();

            enemyImages.add(spriteImage.getSubimage(0, 55, 50, 55));
            enemyImages.add(spriteImage.getSubimage(51, 55, 50, 55));
            enemyImages.add(spriteImage.getSubimage(103, 55, 50, 55));
            enemyImages.add(spriteImage.getSubimage(154, 55, 52, 55));
            enemyImages.add(spriteImage.getSubimage(206, 55, 52, 55));
            enemyImages.add(spriteImage.getSubimage(258, 55, 52, 55));
            enemyImages.add(spriteImage.getSubimage(311, 55, 50, 55));
            enemyImages.add(spriteImage.getSubimage(364, 55, 48, 55));

            for (Image image: enemyImages) {
                scaledEnemies.add(image.getScaledInstance(Game.UP_SCALE, Game.UP_SCALE, Image.SCALE_DEFAULT));
            }

            enemyImages = scaledEnemies;
        } catch (IOException ioe) {
            // do nothing
        }
    }

    // EFFECTS: does nothing. Inheritors of this class can choose to override it if necessary.
    public void updateButtonListeners() {
        //
    }
}
