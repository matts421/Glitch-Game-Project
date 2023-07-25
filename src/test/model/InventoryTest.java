package model;

import com.googlecode.lanterna.TextColor;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    Inventory inventory;
    @BeforeEach
    public void runBefore() {
        inventory = new Inventory();
    }

    @Test
    public void testConstructor() {
        assertTrue(inventory.getItems().isEmpty());
    }

    @Test
    public void testGetQuantityNotInInventory() {
        Item item = new Item("1", TextColor.ANSI.WHITE, 0,0);
        assertEquals(0, inventory.getQuantity(item));
    }

    @Test
    public void testGetQuantityInInventory() {
        Item item = new Item("1", TextColor.ANSI.WHITE, 0,0);
        inventory.addItem(item, 1);
        assertEquals(1, inventory.getQuantity(item));
    }

    @Test
    public void testAddItemNotInOnce() {
        Item item1 = new Item("1", TextColor.ANSI.WHITE, 0, 0);
        inventory.addItem(item1, 1);

        assertTrue(inventory.getItems().contains(item1));
        assertEquals(1, inventory.getItems().size());
        assertEquals(1, inventory.getQuantity(item1));
    }

    @Test
    public void testAddItemManyTimes() {
        Item item1 = new Item("1", TextColor.ANSI.WHITE, 0, 0);
        inventory.addItem(item1, 1);
        inventory.addItem(item1, 5);
        inventory.addItem(item1, 10);

        assertTrue(inventory.getItems().contains(item1));
        assertEquals(1, inventory.getItems().size());
        assertEquals(16, inventory.getQuantity(item1));
    }

    @Test
    public void testAddManyItems() {
        Item item1 = new Item("1", TextColor.ANSI.WHITE, 0, 0);
        Item item2 = new Item("2", TextColor.ANSI.WHITE, 0, 0);
        Item item3 = new Item("3", TextColor.ANSI.WHITE, 0, 0);

        inventory.addItem(item1, 1);
        inventory.addItem(item2, 5);
        inventory.addItem(item3, 10);
        inventory.addItem(item2, 1);

        assertTrue(inventory.getItems().contains(item1));
        assertTrue(inventory.getItems().contains(item2));
        assertTrue(inventory.getItems().contains(item3));

        assertEquals(3, inventory.getItems().size());

        assertEquals(1, inventory.getQuantity(item1));
        assertEquals(6, inventory.getQuantity(item2));
        assertEquals(10, inventory.getQuantity(item3));
    }

    @Test
    public void testRemoveAllQuantityOfItem() {
        Item item1 = new Item("1", TextColor.ANSI.WHITE, 0, 0);

        inventory.addItem(item1, 1);

        inventory.removeItem(item1, 1);

        assertFalse(inventory.getItems().contains(item1));
    }

    @Test
    public void testRemoveManyItems() {
        Item item1 = new Item("1", TextColor.ANSI.WHITE, 0, 0);
        Item item2 = new Item("2", TextColor.ANSI.WHITE, 0, 0);
        Item item3 = new Item("3", TextColor.ANSI.WHITE, 0, 0);

        inventory.addItem(item1, 1);
        inventory.addItem(item2, 5);
        inventory.addItem(item3, 10);
        inventory.addItem(item2, 1);


        inventory.removeItem(item2, 5);
        inventory.removeItem(item3, 9);

        assertTrue(inventory.getItems().contains(item1));
        assertTrue(inventory.getItems().contains(item2));
        assertTrue(inventory.getItems().contains(item3));

        assertEquals(3, inventory.getItems().size());

        assertEquals(1, inventory.getQuantity(item1));
        assertEquals(1, inventory.getQuantity(item2));
        assertEquals(1, inventory.getQuantity(item3));
    }

    @Test
    public void testToJsonEmptyInventory() {
        JSONObject jsonObject = inventory.toJson();
        assertEquals("{}", jsonObject.toString());
    }

    @Test
    public void testToJsonNonEmpty() {
        Item i1 = new Item("1", TextColor.ANSI.WHITE, 0, 0);
        Item i2 = new Item("2", TextColor.ANSI.WHITE, 0, 0);
        Item i3 = new Item("3", TextColor.ANSI.WHITE, 0, 0);

        inventory.addItem(i1, 1);
        inventory.addItem(i2, 5);
        inventory.addItem(i3, 10);

        JSONObject jsonObject = inventory.toJson();

        JSONObject i1Json = jsonObject.getJSONObject("1");
        JSONObject i2Json = jsonObject.getJSONObject("2");
        JSONObject i3Json = jsonObject.getJSONObject("3");

        assertEquals("1", i1Json.getJSONObject("1").getString("name"));
        assertEquals(1, i1Json.getInt("quantity"));
        assertEquals("2", i2Json.getJSONObject("2").getString("name"));
        assertEquals(5, i2Json.getInt("quantity"));
        assertEquals("3", i3Json.getJSONObject("3").getString("name"));
        assertEquals(10, i3Json.getInt("quantity"));

    }

    /*
    JSONObject json = new JSONObject();
        for (Item item: items.keySet()) {
            JSONObject itemJson = new JSONObject();
            itemJson.put(item.getName(), item.toJson());
            itemJson.put("quantity", getQuantity(item));
            json.put(item.getName(), itemJson);
        }
        return json;
     */
}
