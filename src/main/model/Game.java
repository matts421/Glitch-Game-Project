package model;

import com.googlecode.lanterna.input.KeyType;

import java.awt.*;
import java.util.ArrayList;

import static com.googlecode.lanterna.input.KeyType.*;

public class Game {
    public static final int TICKS_PER_SECOND = 10;
    public static final int JUMP_HEIGHT = 5;
    public static final int GRAVITY = 1;
    private boolean ended;
    private final int maxX;
    private final int maxY;
    private GameCharacter player;
    private GameMap map;
    private KeyType previousPress;

    public Game(int maxX, int maxY, GameCharacter player, GameMap map) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.player = player;
        ended = false;
        this.map = map;
    }

    public boolean isEnded() {
        return ended;
    }

    public void doAction(KeyType type) {
        if (type == ArrowLeft) {
            player.run(-1);
            previousPress = ArrowLeft;
        } else if (type == ArrowRight) {
            player.run(1);
            previousPress = ArrowRight;
        } else if (type == ArrowUp) {
            player.jump(maxJump());
        }
    }

    public void tick() {
        if (!onPlatform()) {
            player.fall();
        } else {
            player.setAirborne(false);
        }

        if (wallCollision()) {
            switch (previousPress) {
                case ArrowLeft:
                    doAction(ArrowRight);
                case ArrowRight:
                    doAction(ArrowLeft);
            }
        }
    }

    public boolean onPlatform() {
        for (Rectangle barrier : map.getBarriers()) {
            if (barrier.y == player.getPosY() + 1
                    && barrier.x <= player.getPosX()
                    && (barrier.x + barrier.width) >= player.getPosX()) {
                return true;
            }
        }
        return false;
    }

    public boolean wallCollision() {
        for (Rectangle barrier: map.getBarriers()) {
            if (barrier.intersects(player.getModel())) {
                return true;
            }
        }
        return false;
    }

    public int closestBelowPlatform() {
        int bestDist = -1;
        for (Rectangle barrier : map.getBarriers()) {
            if (barrier.y > player.getPosY()
                    && barrier.x <= player.getPosX()
                    && (barrier.x + barrier.width) >= player.getPosX()) {
                int currentDist = (player.getPosY() - barrier.y);
                if (bestDist == -1 || currentDist < bestDist) {
                    bestDist = currentDist;
                }
            }
        }
        return bestDist;
    }

    public int closestAbovePlatform() {
        int bestDist = -1;
        for (Rectangle barrier : map.getBarriers()) {
            if (barrier.y < player.getPosY()
                    && barrier.x <= player.getPosX()
                    && (barrier.x + barrier.width) >= player.getPosX()) {
                int currentDist = (player.getPosY() - barrier.y - 1);
                if (bestDist == -1 || currentDist < bestDist) {
                    bestDist = currentDist;
                }
            }
        }
        return bestDist;
    }

    public GameCharacter getPlayer() {
        return player;
    }

    public GameMap getMap() {
        return map;
    }

    private int maxJump() {
        int bestDist = closestAbovePlatform();
        if (bestDist == -1 || bestDist > JUMP_HEIGHT) {
            return JUMP_HEIGHT;
        } else {
            return bestDist;
        }
    }
}
