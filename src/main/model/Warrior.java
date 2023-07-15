package model;

public class Warrior extends GameCharacter {
    private static final int MAX_HEALTH = 50;
    private static final int MAX_DEFENSE = 100;
    private static final int MAX_MANA = 50;

    public Warrior(boolean difficulty) {
        super(MAX_MANA, MAX_HEALTH, MAX_DEFENSE, difficulty);
    }

    // MODIFIES: this
    // EFFECTS: perform basic attack action in direction
    //          currently facing.
    @Override
    public void basicAttack() {

    }

    // REQUIRES: getMana() > 0
    // MODIFIES: this
    // EFFECTS: perform special attack action in direction
    //          currently facing. Begins cool-down for next
    //          specialAttack and de-increments mana.
    @Override
    public void specialAttack() {

    }
}
