package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectileTest {
    Projectile projectile;
    GameCharacter gc;

    @BeforeEach
    public void runBefore() {
        gc = new GameCharacter(0, 0, TextColor.ANSI.WHITE);
        gc.setPosX(0);
        gc.setPosY(0);
        gc.updateModel();

        projectile = new Projectile(gc);
    }

    @Test
    public void testConstructor() {
        assertEquals(1, projectile.getDirection());
        assertEquals(0, projectile.getPosX());
        assertEquals(0, projectile.getPosY());
        assertEquals(TextColor.ANSI.WHITE, projectile.getColor());

        assertEquals(0, projectile.getModel().x);
        assertEquals(0, projectile.getModel().y);
        assertEquals(1, projectile.getModel().width);
        assertEquals(1, projectile.getModel().height);
    }

    @Test
    public void testMoveProjectileOnce() {
        projectile.move();

        assertEquals(1, projectile.getPosX());
        assertEquals(1, projectile.getModel().x);
    }

    @Test
    public void testMoveProjectileManyTimes() {
        projectile.move();
        projectile.move();
        projectile.move();

        assertEquals(3, projectile.getPosX());
        assertEquals(3, projectile.getModel().x);
    }
}
