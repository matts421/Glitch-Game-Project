package model;

import com.googlecode.lanterna.TextColor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectileTest {
    Projectile projectile;
    GameCharacter gc;

    @BeforeEach
    public void runBefore() {
        Inventory inventory = new Inventory();
        Rectangle model = new Rectangle(0, 0, 1, 1);

        gc = new GameCharacter(0, 0, 1, false, 0, 0,
                inventory, model, TextColor.ANSI.WHITE);
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
        assertEquals(Game.UP_SCALE, projectile.getModel().width);
        assertEquals(Game.UP_SCALE, projectile.getModel().height);
    }

    @Test
    public void testConstructor2() {
        Projectile p2 = new Projectile(0, 0, 1, TextColor.ANSI.WHITE,
                new Rectangle(0, 0, 1, 1));
        assertEquals(1, p2.getDirection());
        assertEquals(0, p2.getPosX());
        assertEquals(0, p2.getPosY());
        assertEquals(TextColor.ANSI.WHITE, p2.getColor());

        assertEquals(0, p2.getModel().x);
        assertEquals(0, p2.getModel().y);
        assertEquals(1, p2.getModel().width);
        assertEquals(1, p2.getModel().height);

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

    @Test
    public void testToJson() {
        JSONObject jsonObject = projectile.toJson();
        JSONArray position = jsonObject.getJSONArray("position");

        assertEquals(position.get(0), 0);
        assertEquals(position.get(1), 0);
        assertEquals(jsonObject.getInt("direction"), 1);
    }
}
