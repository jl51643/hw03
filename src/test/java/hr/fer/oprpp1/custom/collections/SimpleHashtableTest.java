package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleHashtableTest {

    @Test
    public void testSimpleHashtableConstructors() {
        SimpleHashtable<String, String> m = new SimpleHashtable<>();
        SimpleHashtable<String, String> n = new SimpleHashtable<>(7);
        assertThrows(IllegalArgumentException.class, () -> {SimpleHashtable<String, String> o = new SimpleHashtable<>(-7);});
    }

    @Test
    public void putTest() {
        SimpleHashtable<String, String> s = new SimpleHashtable<>();
        assertThrows(NullPointerException.class,() -> s.put(null, "value"));
        assertNull(s.put("key1", "value1"));
        assertEquals("value1", s.put("key1", "value2"));
        SimpleHashtable<String, String> smallTable = new SimpleHashtable<>(1);
        smallTable.put("key1", "value1");
        smallTable.put("key2", "value2");
        smallTable.put("key3", "value1");
        smallTable.put("key4", "value2");
        assertEquals(4, smallTable.size());

        SimpleHashtable<String, String> m = new SimpleHashtable<>(4);
        m.put("k1", "v1");
        m.put("k11", "v1");
        m.put("k2", "v1");
        m.put("k4", "v1");
        m.put("k2", "v11");
        m.put("k5", "v1");
        m.put("k55", "v1");
        m.put("k46", "v1");
        m.put("k2", "v111");
        m.put("k56", "v1");
        m.put("k24", "v1");
        m.put("k1111", "v1");
        m.put("k89", "v1");
        m.put("k454", "v1");
        m.put("k2345", "v1");
        m.put("k9", "v1");
        m.put("k90", "v1");
        m.put("k19", "v1");
        m.put("k199", "v1");
        assertTrue(m.size() == 17);
        assertTrue(m.get("k2").equals("v111"));
    }

    @Test
    public void getTest() {
        SimpleHashtable<Integer, Integer> s = new SimpleHashtable<>();
        assertNull(s.get("nonExistingKey"));
        s.put(1,2);
        assertEquals(2, s.get(1));
        s.put(1, 3);
        assertEquals(3, s.get(1));
        assertNull(s.get(null));
    }

    @Test
    public void sizeTest() {
        SimpleHashtable<String, String> s = new SimpleHashtable<>(1);
        assertEquals(0, s.size());
        s.put("key1", "value1");
        assertEquals(1, s.size());
        s.put("key1", "otherValue");
        assertEquals(1, s.size());
        s.put("key2", "value1");
        assertEquals(2, s.size());
    }

    @Test
    public void containsKeyTest() {
        SimpleHashtable<Integer, String> s = new SimpleHashtable<>();
        assertFalse(s.containsKey(1));
        s.put(1, "value");
        assertTrue(s.containsKey(1));
        s.put(1, "value");
        s.put(7, "value");
        s.put(5, "value");
        s.put(9, "value");
        assertTrue(s.containsKey(5));
        assertFalse(s.containsKey(6));
    }

    @Test
    public void containsValueTest() {
        SimpleHashtable<String, String> s = new SimpleHashtable<>();
        assertFalse(s.containsValue("anyValue"));
        s.put("key1", "value1");
        assertTrue(s.containsValue("value1"));
        s.put("key1", "changedValue");
        assertFalse(s.containsValue("value1"));
        assertTrue(s.containsValue("changedValue"));
        s.put("key1", "value2");
        s.put("key2", "value3");
        s.put("key3", "value1");
        s.put("key4", "value2");
        s.put("key5", "value3");
        assertTrue(s.containsValue("value3"));
        assertTrue(s.containsValue("value1"));
        assertTrue(s.containsValue("value2"));
        assertFalse(s.containsValue("v"));
        s.put("key", null);
        assertTrue(s.containsValue(null));

    }

    @Test
    public void removeTest() {
        SimpleHashtable<String, String> s = new SimpleHashtable<>(1);
        assertNull(s.remove("anyKey"));
        s.put("key1", "value1");
        assertEquals("value1", s.remove("key1"));
        assertEquals(0, s.size());
        assertNull(s.remove(null));
        s.put("key1", "value1");
        s.put("key2", "value3");
        s.put("key3", "value4");
        s.put("key4", "value2");
        s.put("key5", "value1");
        s.put("key6", "value4");
        assertEquals("value4", s.remove("key3"));
        s.put("key33", "value");
        assertEquals("value4", s.remove("key6"));
    }

    @Test
    public void isEmptyTest() {
        SimpleHashtable<String, String> s = new SimpleHashtable<>();
        assertTrue(s.isEmpty());
        s.put("k", "v");
        assertFalse(s.isEmpty());
        s.remove("k");
        assertTrue(s.isEmpty());
        s.put("k", null);
        assertFalse(s.isEmpty());
    }

    @Test
    public void clearTest() {
        SimpleHashtable<String, String> s = new SimpleHashtable<>();
        s.clear();
        assertTrue(s.isEmpty());
        s.put("k1", "v1");
        s.put("k7", "v1");
        s.put("k4", "v1");
        s.put("k6", "v1");
        s.put("k2", "v1");
        assertFalse(s.isEmpty());
        s.clear();
        assertTrue(s.isEmpty());
        assertEquals(0, s.size());
    }

    @Test
    public void testToString() {
        SimpleHashtable<String, String> s = new SimpleHashtable<>();
        assertEquals("[]", s.toString());
        s.put("k1", "v1");
        s.put("k7", "v1");
        s.put("k4", "v1");
        s.put("k6", "v1");
        s.put("k2", "v1");
        assertEquals("[k1=v1, k2=v1, k4=v1, k6=v1, k7=v1]", s.toString());
        s.clear();
        assertEquals("[]", s.toString());
        
    }

    @Test
    public void toArrayTest() {
        SimpleHashtable<String, String> s = new SimpleHashtable<>();
        s.put("k1", "v1");
        s.put("k7", "v1");
        s.put("k4", "v1");
        s.put("k6", "v1");
        s.put("k2", "v1");
        SimpleHashtable.TableEntry<String, String>[] array = s.toArray();
        assertEquals(s.size(), array.length);
        assertArrayEquals(array, s.toArray());
    }

    @Test
    public void iteratorTest() {
        SimpleHashtable<String, String> s = new SimpleHashtable<>(2);
        s.put("k1", "v1");
        s.put("k7", "v7");
        s.put("k4", "v4");
        s.put("k6", "v6");
        s.put("k2", "v2");
        Iterator<SimpleHashtable.TableEntry<String, String>> iterator = s.iterator();
        /*while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }*/
        assertEquals(s.get("k4"), iterator.next().getValue());
        assertEquals(s.get("k6"), iterator.next().getValue());
        iterator.remove();
        assertThrows(IllegalStateException.class, () -> iterator.remove());
        assertEquals(s.get("k7"), iterator.next().getValue());
        s.put("k", "v");
        assertThrows(ConcurrentModificationException.class, () -> iterator.next());
        assertThrows(ConcurrentModificationException.class, () -> iterator.hasNext());
        assertThrows(ConcurrentModificationException.class, () -> iterator.remove());

        System.out.println(s.toString());
        Iterator<SimpleHashtable.TableEntry<String, String>> it = s.iterator();
        assertThrows(NoSuchElementException.class, () -> {
            for (int i = 0; i <= s.size(); i++) {
                it.next();
            }
        });
    }

    @Test
    public void iteratorPrimjerTest() {
        SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();

        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5); // overwrites old grade for Ivana
        String s = "";
        for(SimpleHashtable.TableEntry<String,Integer> pair : examMarks) {
            System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
            s += pair.getKey() + " => " + pair.getValue() + "\n";
        }
        assertEquals("Ivana => 5\n" +
                "Kristina => 5\n" +
                "Ante => 2\n" +
                "Jasna => 2\n", s);

        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5);
        Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
        while(iter.hasNext()) {
            SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
            if(pair.getKey().equals("Ivana")) {
                iter.remove();
                assertThrows(IllegalStateException.class, () -> iter.remove());

            }
        }



        System.out.println("\n");
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5);
        Iterator<SimpleHashtable.TableEntry<String,Integer>> iter3 = examMarks.iterator();
        while(iter3.hasNext()) {
            SimpleHashtable.TableEntry<String,Integer> pair = iter3.next();
            System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
            iter3.remove();
        }
        System.out.printf("Veličina: %d%n", examMarks.size());
        assertTrue(examMarks.size() == 0);

    }

    @Test
    public void testIteratorRemoveConcurentModification() {
        SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5);
        Iterator<SimpleHashtable.TableEntry<String,Integer>> iter2 = examMarks.iterator();
        assertThrows(ConcurrentModificationException.class, () -> {
            while(iter2.hasNext()) {
                SimpleHashtable.TableEntry<String,Integer> pair = iter2.next();
                if(pair.getKey().equals("Ivana")) {
                    examMarks.remove("Ivana");

                }
            }
        });

    }

    @Test
    public void testIterartorNullValueRemove() {
        SimpleHashtable<String, String> s = new SimpleHashtable<>();
        s.put("k1", null);
        s.put("k2", null);
        s.put("k3", null);
        s.put("k4", null);
        Iterator<SimpleHashtable.TableEntry<String,String>> iter = s.iterator();
        iter.remove();
        assertThrows(IllegalStateException.class, () -> iter.remove());
    }

}