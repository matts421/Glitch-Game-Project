package ui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class GamePanel extends MainGamePanel {
    private static final String OVER = "Game Over!";
    private static final String REPLAY = "R to replay";

    // Constructs a game panel
    // effects:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public GamePanel(Game g) {
        super(g);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);

        if (game.isEnded()) {
            gameOver(g);
        }
    }

    // Draws the game
    // modifies: g
    // effects:  draws the game onto g
    private void drawGame(Graphics g) {
        drawPlayer(g);
        drawBarriers(g);
        drawProjectiles(g);
        drawEnemies(g);
        drawItems(g);
    }

    // MODIFIES: this
    // EFFECTS: draws on the screen the player at their current position in their class' color, represented by
    //          a smile emoticon.
    private void drawPlayer(Graphics g) {
        Color savedCol = g.getColor();
        g.setColor(game.getPlayer().getColor().toColor());
        g.fillRect(game.getPlayer().getPosX(), game.getPlayer().getPosY(),
                1 * Game.UP_SCALE, 1 * Game.UP_SCALE);
        g.setColor(savedCol);
    }

    // MODIFIES: this
    // EFFECTS: draws all the map's items on the screen in their respective positions, represented by
    //          a dollar sign emoticon.
    private void drawItems(Graphics g) {
        for (Item item : game.getMap().getItems().getItems()) {
            int quantity = game.getMap().getItems().getQuantity(item);
            drawItem(g, item, quantity);
        }
    }

    private void drawItem(Graphics g, Item i, int quantity) {
        Color savedCol = g.getColor();
        g.setColor(i.getColor().toColor());
        g.fillOval(i.getPosX(), i.getPosY(), 1 * Game.UP_SCALE, 1 * Game.UP_SCALE);
        g.setColor(savedCol);
        g.drawString(Integer.toString(quantity), i.getPosX(), i.getPosY());
    }

    // MODIFIES: this
    // EFFECTS: draws all the map's enemies on the screen in their respective positions, represented by
    //          the enemy's RENDER_MODEL
    private void drawEnemies(Graphics g) {
        for (Enemy enemy : game.getMap().getEnemies()) {
            drawEnemy(g, enemy);
        }
    }

    private void drawEnemy(Graphics g, Enemy e) {
        Color savedCol = g.getColor();
        g.setColor(e.getColor().toColor());
        g.fillOval(e.getPosX(), e.getPosY(), 1 * Game.UP_SCALE, 1 * Game.UP_SCALE);
        g.setColor(savedCol);
    }

    // MODIFIES: this
    // EFFECTS: draws all the map's projectiles on the screen in their respective positions, represented by
    //          an arrow in their respective directions.
    private void drawProjectiles(Graphics g) {
        for (Projectile projectile : game.getMap().getProjectiles()) {
            drawProjectile(g, projectile);
        }
    }

    private void drawProjectile(Graphics g, Projectile p) {
        Color savedCol = g.getColor();
        g.setColor(p.getColor().toColor());
        g.fillOval(p.getPosX(), p.getPosY(), 1 * Game.UP_SCALE, 1 * Game.UP_SCALE);
        g.setColor(savedCol);
    }

    // MODIFIES: this
    // EFFECTS: draws all the map's barriers on the screen at their respective positions, represented by
    //          a block character.
    private void drawBarriers(Graphics g) {
        for (Rectangle barrier : game.getMap().getBarriers()) {
            drawBarrier(g, barrier);
        }
    }

    private void drawBarrier(Graphics g, Rectangle rect) {
        Color savedCol = g.getColor();
        g.setColor(new Color(255, 255, 255));
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        g.setColor(savedCol);
    }


    // Draws the "game over" message and replay instructions
    // modifies: g
    // effects:  draws "game over" and replay instructions onto g
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(OVER, g, fm, Game.HEIGHT / 2);
        centreString(REPLAY, g, fm, Game.HEIGHT / 2 + 50);
        g.setColor(saved);
    }

    // Centres a string on the screen
    // modifies: g
    // effects:  centres the string str horizontally onto g at vertical position yPos
    private void centreString(String str, Graphics g, FontMetrics fm, int posY) {
        int width = fm.stringWidth(str);
        g.drawString(str, (Game.WIDTH - width) / 2, posY);
    }
}
