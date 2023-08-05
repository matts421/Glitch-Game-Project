package ui;

import com.googlecode.lanterna.TextColor;
import model.*;
import java.awt.*;

// A class that represents the actual game panel including the character, enemies, etc.
// NOTE: This class was inspired by work done in the SpaceInvaders game.
public class GamePanel extends MainGamePanel {
    private static final String OVER = "Game Over!";
    private static final String EXIT = "ESC to exit";

    // EFFECTS:  Sets size and background color of panel,
    //           updates this with the game to be displayed
    public GamePanel(Game g) {
        super(g);
    }

    // MODIFIES: g
    // EFFECTS: Draws the current state of the game. If game is over,
    //          display Game Over screen.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);

        if (game.isEnded()) {
            game.getPlayer().setPosX(game.getMaxX() * 2);
            gameOver(g);
        }
    }

    // MODIFIES: g
    // EFFECTS: Draws the game onto g
    private void drawGame(Graphics g) {
        drawPlayer(g);
        drawBarriers(g);
        drawProjectiles(g);
        drawEnemies(g);
        drawItems(g);
    }

    // MODIFIES: g
    // EFFECTS: Draws on the screen the player at their current position in their class' color, represented by
    //          a square.
    private void drawPlayer(Graphics g) {
        Color savedCol = g.getColor();
        g.setColor(game.getPlayer().getColor().toColor());
        g.fillRect(game.getPlayer().getPosX(), game.getPlayer().getPosY(),
                Game.UP_SCALE, Game.UP_SCALE);
        g.setColor(savedCol);
    }

    // MODIFIES: g
    // EFFECTS: Draws all the map's items on the screen in their respective positions, represented by
    //          green circles.
    private void drawItems(Graphics g) {
        for (Item item : game.getMap().getItems().getItems()) {
            int quantity = game.getMap().getItems().getQuantity(item);
            drawItem(g, item, quantity);
        }
    }

    // MODIFIES: g
    // EFFECTS: Draws an individual item with quantity at the respective position on the map
    private void drawItem(Graphics g, Item i, int quantity) {
        Color savedCol = g.getColor();
        g.setColor(i.getColor().toColor());
        g.fillOval(i.getPosX(), i.getPosY(), Game.UP_SCALE, Game.UP_SCALE);
        g.setColor(savedCol);
        g.drawString(Integer.toString(quantity), i.getPosX(), i.getPosY());
    }

    // MODIFIES: g
    // EFFECTS: Draws all the map's enemies on the screen in their respective positions, represented by
    //          a pink circle.
    private void drawEnemies(Graphics g) {
        for (Enemy enemy : game.getMap().getEnemies()) {
            drawEnemy(g, enemy);
        }
    }

    // MODIFIES: g
    // EFFECTS: Draws a single enemy at the respective position onto g.
    private void drawEnemy(Graphics g, Enemy e) {
        Color savedCol = g.getColor();
        g.setColor(e.getColor().toColor());
        g.fillOval(e.getPosX(), e.getPosY(), Game.UP_SCALE, Game.UP_SCALE);
        g.setColor(savedCol);
    }

    // MODIFIES: g
    // EFFECTS: draws all the map's projectiles on the screen in their respective positions, represented by
    //          a circle.
    private void drawProjectiles(Graphics g) {
        for (Projectile projectile : game.getMap().getProjectiles()) {
            drawProjectile(g, projectile);
        }
    }

    // MODIFIES: g
    // EFFECTS: draws a single projectile at its respective position onto g.
    private void drawProjectile(Graphics g, Projectile p) {
        Color savedCol = g.getColor();
        g.setColor(p.getColor().toColor());
        g.fillOval(p.getPosX(), p.getPosY(), Game.UP_SCALE, Game.UP_SCALE);
        g.setColor(savedCol);
    }

    // MODIFIES: g
    // EFFECTS: draws all the map's barriers on the screen at their respective positions, represented by
    //          a white rectangle.
    private void drawBarriers(Graphics g) {
        for (Rectangle barrier : game.getMap().getBarriers()) {
            drawBarrier(g, barrier);
        }
    }

    // MODIFIES: g
    // EFFECTS: draws a single map barrier at its respective position onto g.
    private void drawBarrier(Graphics g, Rectangle rect) {
        Color savedCol = g.getColor();
        g.setColor(new Color(255, 255, 255));
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        g.setColor(savedCol);
    }


    // MODIFIES: g
    // EFFECTS: Draws "game over", displays number of coins, and gives exit instructions onto g.
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        Item coin = new Item("coin", TextColor.ANSI.GREEN, 0,0);
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(OVER, g, fm, Game.HEIGHT / 2);
        centreString("You finished with " + game.getPlayer().getInventory().getQuantity(coin) + " coins.",
                g, fm, Game.HEIGHT / 2 + 50);
        centreString(EXIT, g, fm, Game.HEIGHT / 2 + 100);
        g.setColor(saved);
    }

    // MODIFIES: g
    // EFFECTS: Centres the string str horizontally onto g at vertical position yPos
    // NOTE: this method was directly reproduced from SpaceInvaders.
    private void centreString(String str, Graphics g, FontMetrics fm, int posY) {
        int width = fm.stringWidth(str);
        g.drawString(str, (Game.WIDTH - width) / 2, posY);
    }
}
