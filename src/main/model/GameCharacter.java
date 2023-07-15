package model;

import java.awt.*;

public abstract class GameCharacter {
    private int health;
    private int mana;
    private int defense;
    private boolean hardcore;
    private int posX;
    private int posY;
    private Rectangle model;
    private boolean airborne;


    public GameCharacter(int health, int mana, int defense, boolean difficulty) {
        this.health = health;
        this.mana = mana;
        this.defense = defense;
        hardcore = difficulty;
        posX = 0;
        posY = 10;
        model = new Rectangle(posX, posY,1, 1);
    }

    public abstract void basicAttack();

    public abstract void specialAttack();

    public void run(int dir) {
        posX += dir;
        model.x = posX;
    }

    public void jump(int height) {
        if (!airborne) {
            posY -= height;
            model.y = posY;
            airborne = true;
        }
    }

    public void fall() {
        posY += Game.GRAVITY;
        model.y = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Rectangle getModel() {
        return model;
    }

    public boolean isAirborne() {
        return airborne;
    }

    public void setAirborne(boolean b) {
        airborne = b;
    }

}
