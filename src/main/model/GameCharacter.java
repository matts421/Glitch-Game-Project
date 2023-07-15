package model;

public abstract class GameCharacter {
    private static final int JUMP_HEIGHT = 5;
    private int mana;
    private int health;
    private int defense;
    private int posX;
    private int posY;
    private boolean idle;
    private boolean airborne;
    private boolean hardcore;
    private int direction;

    public GameCharacter(int initialMana,
                         int initialHealth,
                         int initialDefense,
                         boolean difficulty) {
        health = initialHealth;
        mana = initialMana;
        defense = initialDefense;
        posX = 0;
        posY = 10;
        idle = true;
        airborne = false;
        hardcore = difficulty;
        direction = 1;
    }

    public abstract void basicAttack();

    public abstract void specialAttack();

    // REQUIRES: dir == -1 || 1
    // MODIFIES: this
    // EFFECTS: perform run action in direction of dir.
    //          0 runs left, and 1 runs right.
    //          If currently facing opposite direction to dir,
    //          change direction first.
    public void run(int dir) {
        posX += dir;
    }

    // REQUIRES: dir == -1 || 1
    //           AND isAirborne = false
    // MODIFIES: this
    // EFFECTS: perform slide action in direction of dir.
    //          0 slides left, and 1 slides right.
    //          If currently facing opposite direction to dir,
    //          change direction first.
    public void slide(int dir){

    }

    // REQUIRES: dir == -1 || 1
    //           AND isAirborne = false
    // MODIFIES: this
    // EFFECTS: perform roll action in direction of dir.
    //          0 rolls left, and 1 rolls right.
    //          If currently facing opposite direction to dir,
    //          change direction first.
    public void roll(int dir){

    }

    // REQUIRES: dir == -1 || 1
    //           AND isAirborne = false
    // MODIFIES: this
    // EFFECTS: perform defend action in direction of dir.
    //          0 defends left, and 1 defends right.
    //          If currently facing opposite direction to dir,
    //          change direction first.
    public void defend(int dir){

    }

    // REQUIRES: getHealth() * getDefense() > damage
    // MODIFIES: this
    // EFFECTS: de-increment health by damage
    public void takeDamage(int damage) {

    }

    // MODIFIES: this
    // EFFECTS: perform jump move by increasing y-position.
    //          Sets isAirborne = true.
    public void jump() {
        if (airborne) {
            return;
        } else {
            posY -= JUMP_HEIGHT;
            airborne = true;
        }

    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: adds amount to current mana
    public void gainMana(int amount) {
        //stub
    }

    // MODIFIES: this
    // EFFECTS: changes idle from true to false, or vice versa.
    public void toggleIdle() {
        idle = !idle;
    }

    // MODIFIES: this
    // EFFECTS: lowers Y position to experience gravity
    public void fall(int maxY) {
        posY += Game.GRAVITY;
        if (posY >= maxY) {
            airborne = false;
        }
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getHealth() {
        return health;
    }

    public int getDefense() {
        return defense;
    }

    public int getMana() {
        return mana;
    }

    public int getDirection() {
        return direction;
    }

    public boolean isIdle() {
        return idle;
    }

    public boolean isAirborne() {
        return airborne;
    }

}
