package core;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MyMapTest {
    private Map<Long, String> myMapExample;

    @Before
    public void setUp() {
        myMapExample = new MyHashMap<>();
        myMapExample.put(1L, "First");
        myMapExample.put(2L, "Second");
        myMapExample.put(3L, "Third");
    }

    @After
    public void tearDown() {
        myMapExample = null;
    }

    @Test
    public void myHashMap_size_Ok() {
        int expected = 3;
        int actual = myMapExample.size();
        assertEquals(expected, actual);
    }

    @Test
    public void myHashMap_isEmpty_Ok() {
        myMapExample.clear();
        assertTrue(myMapExample.isEmpty());
    }

    @Test
    public void myHashMap_containsKey_Ok() {
        assertTrue(myMapExample.containsKey(2L));
    }

    @Test
    public void myHashMap_containsKey_NotOk() {
        assertFalse(myMapExample.containsKey(4L));
    }

    @Test
    public void myHashMap_containsValue_Ok() {
        assertTrue(myMapExample.containsValue("Third"));
    }

    @Test
    public void myHashMap_containsValue_NotOk() {
        assertFalse(myMapExample.containsValue("Fifth"));
    }

    @Test
    public void myHashMap_get_Ok() {
        String expected = "First";
        String actual = myMapExample.get(1L);
        assertEquals(expected, actual);
    }

    @Test
    public void myHashMap_get_NotOk() {
        assertEquals(myMapExample.get(4L), null);
    }

    @Test
    public void myHashMap_remove_Ok() {
        myMapExample.put(19L, "Nineteenth");
        assertEquals("Third",myMapExample.remove(3L));
        assertTrue(myMapExample.size() == 3);
    }

    @Test
    public void myHashMap_remove_NotOk() {
        assertTrue(myMapExample.remove(4L) == null);
        assertTrue(myMapExample.size() == 3);
    }

    @Test
    public void myHashMap_putAll_Ok() {
        Map<Long, String> putAllMap = new MyHashMap<>();
        putAllMap.put(4L, "Fourth");
        putAllMap.put(5L, "Fifth");
        putAllMap.put(6L, "Sixth");
        myMapExample.putAll(putAllMap);
        assertTrue(myMapExample.size() == 6);
        assertEquals("Fifth", myMapExample.get(5L));
    }

    @Test
    public void myHashMap_clear_Ok() {
        myMapExample.clear();
        assertTrue(myMapExample.isEmpty());
    }

    @Test
    public void myHashMap_keySet_Ok() {
        Set<Map.Entry<Long, String>> keySet = myMapExample.entrySet();
        assertTrue(keySet.size() == 3);
    }

    @Test
    public void myHashMap_values_Ok() {
        Collection<String> values = myMapExample.values();
        assertTrue(values.size() == 3);
    }

    @Test
    public void myHashMap_entrySet_Ok() {
        Long i = 0L;
        for (Map.Entry<Long, String> entry : myMapExample.entrySet()) {
            i++;
            assertEquals(i, entry.getKey());
            assertEquals(myMapExample.get(i), entry.getValue());
        }
    }
}