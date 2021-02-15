package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DictionaryTest {

    @Test
    public void testDictionaryConstructor() {

        Dictionary<String, String> d = new Dictionary<>();
        assertTrue(d.isEmpty());
    }

    @Test
    public void isEmptyTest() {
        Dictionary<String, String> d = new Dictionary<>();
        assertTrue(d.isEmpty());
        d.put("key1", "value1");
        assertFalse(d.isEmpty());
        d.clear();
        assertTrue(d.isEmpty());
    }

    @Test
    public void sizeTest() {
        Dictionary<String, String> d = new Dictionary<>();
        assertEquals(0, d.size());
        d.put("key1", "value");
        assertEquals(1, d.size());
    }

    @Test
    public void clearTest() {
        Dictionary<Integer, Integer> d = new Dictionary<>();
        d.put(1,1);
        d.put(2,2);
        d.put(3,3);
        assertFalse(d.isEmpty());
        d.clear();
        assertTrue(d.isEmpty());
    }

    @Test
    public void putTest() {
        Dictionary<String, String> d = new Dictionary<>();
        assertThrows(NullPointerException.class, () -> d.put(null, "value1"));
        d.put("key1", "value1");
        assertEquals("value1", d.get("key1"));
        d.put("key1", "changedValue");
        assertEquals("changedValue", d.get("key1"));
        assertEquals("changedValue", d.put("key1", "thirdValue"));
        assertEquals("thirdValue", d.get("key1"));
        assertNull(d.put("key2", "value1"));
        d.put("key3", null);
        assertNull(d.get("key3"));

    }

    @Test
    public void getTest() {
        Dictionary<String, String> d = new Dictionary<>();
        assertNull(d.get(null));
        assertNull(d.get("nonExistingKey"));
        assertNull(d.get(1));
        d.put("key1", "value1");
        assertEquals("value1", d.get("key1"));
        d.put("1", "value2");
        assertNull(d.get(1));
    }

    @Test
    public void removeTest() {
        Dictionary<String, String> d = new Dictionary<>();
        assertNull(d.remove("nonExistingKey"));
        d.put("key1", "value1");
        assertEquals("value1", d.remove("key1"));
        assertTrue(d.isEmpty());
        d.put("key1", "value1");
        d.put("key2", "value2");
        d.put("key3", "value4");
        d.put("key4", "value1");
        assertEquals("value4", d.remove("key3"));
        assertNull(d.remove("key3"));
        assertEquals("value1", d.remove("key1"));
        assertNull(d.remove(null));
    }

}