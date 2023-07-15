package model;

import com.googlecode.lanterna.input.KeyType;

public class Game {
    public static final int TICKS_PER_SECOND = 5;
    public static final int GRAVITY = 1;
    private boolean ended;
    private final int maxX;
    private final int maxY;
    private GameCharacter player;

    public Game(int maxX, int maxY, GameCharacter player) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.player = player;
        ended = false;
    }

    public boolean isEnded() {
        return ended;
    }

    public void doAction(KeyType type) {
        if (type == KeyType.ArrowLeft) {
            player.run(-1);
        } else if (type == KeyType.ArrowRight) {
            player.run(1);
        } else if (type == KeyType.ArrowUp) {
            player.jump();
        }
    }

    public void tick() {
        if (player.isAirborne() && player.getPosY() < maxY) {
            player.fall(maxY);
        }
    }

    public GameCharacter getPlayer() {
        return player;
    }
}
