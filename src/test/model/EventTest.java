package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

// Tests for Event class
// NOTE: this class was heavily influenced from AlarmSystem
public class EventTest {
    private Event e;
    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e = new Event("test");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("test", e.getDescription());
        assertEquals(d, e.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "test", e.toString());
    }

    @Test
    public void testEqualsNull() {
        Object o = null;
        assertNotEquals(e, o);
    }

    @Test
    public void testEqualsDifferentClass() {
        String notAnEvent = "hello world";
        assertNotEquals(e, notAnEvent);
    }

    @Test
    public void testHashCode() {
        int result = 13 * e.getDate().hashCode() + e.getDescription().hashCode();
        assertEquals(result, e.hashCode());
    }
}
