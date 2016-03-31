package dictionary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dictionary.Dictionary;
import dictionary.DictionaryEntry;

public abstract class TestDictionary {

    Dictionary<String, Integer> d;

    @Before
    public abstract void setUp();

    @After
    public abstract void tearDown();

    @Test
    public void testIsEmpty() {
        assertTrue("isEmpty() failed for empty dictionary", d.isEmpty());
    }

    @Test
    public void testIsEmptyFalse() {
        d.put("Tigger", 20);
        assertFalse("isEmpty() failed for dictionary with one element", d
                .isEmpty());
    }

    @Test
    public void testClear() {
        d.clear();
        assertTrue("clear() failed for Empty dictionary ", d.isEmpty());
    }

    @Test
    public void testClearWithContent() {
        d.put("Jennyanydots", 4);
        d.clear();
        assertTrue("clear() failed for non-empty dictionary ", d.isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals("size() failed for empty dictionary", 0, d.size());
    }

    @Test
    public void testSizeWithItem() {
        d.put("Skimbleshanks", 30);
        assertEquals("size() failed for single item dictionary", 1, d.size());
    }

    @Test
    public void testSizeWithItems() {
        List<String> cats = Arrays.asList("Growltiger", "Rum Tum Tugger",
                "Jellicles", "Mungojerrie", "Rumpelteazer");
        for (String cat : cats) {
            d.put(cat, cat.hashCode());
        }
        assertEquals("size() failed for multiple cats", cats.size(), d.size());
    }

    @Test
    public void testGet() {
        d.put("Tiddles", 10);
        assertEquals("get() failed for one item dictionary", 10, (int) d
                .get("Tiddles"));
    }

    @Test
    public void testGetMultiple() {
        List<String> cats = Arrays.asList("Bustopher Jones", "Old Deuteronomy",
                "Mr. Mistoffelees", "Gus");
        List<Integer> values = Arrays.asList(7, 2, 8, 20);

        for (int i = 0; i < cats.size(); i++) {
            d.put(cats.get(i), values.get(i));
        }

        assertEquals("get() failed for multiple item dictionary", 8, (int) d
                .get("Mr. Mistoffelees"));
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetException() {
        d.get("Tiddles");
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveEmpty() {
        d.remove("Grizabella");
    }

    @Test
    public void testRemoveSingle() {
        d.put("Tiddles", 10);
        d.remove("Tiddles");
        assertEquals("remove() failed with single item dictionary", 0, d.size());
    }

    @Test
    public void testRemoveMultiple() {
        List<String> cats = Arrays.asList("Gus", "Tiddles", "Skimbleshanks");
        List<Integer> values = Arrays.asList(7, 3, 8);
        for (int i = 0; i < cats.size(); i++) {
            d.put(cats.get(i), values.get(i));
        }

        d.remove("Skimbleshanks");

        assertEquals("remove() failed with multiple item dictionary", 2, d
                .size());
    }

    @Test
    public void testListIteratorEmpty() {
        Iterator<DictionaryEntry<String, Integer>> it = d.iterator();
        assertFalse("Iterator: hasNext() failed with empty dictionary", it
                .hasNext());
    }

    @Test
    public void testListIteratorMany() {
        List<String> cats = Arrays.asList("practical", "dramatical",
                "pragmatical", "fanatical", "oratorical", "delphioracle",
                "skeptical", "dispeptical", "romantical", "pedantical",
                "critical", "parasitical", "allegorical", "metaphorical",
                "statistical", "mystical", "political", "hypocritical",
                "clerical", "hysterical", "cynical", "rabbinical");
        for (int i = 0; i < cats.size(); i++) {
            d.put(cats.get(i), i);
        }

        List<String> sortedCats = new ArrayList<String>(cats);
        Collections.sort(sortedCats);

        Iterator<String> expected = sortedCats.iterator();
        Iterator<DictionaryEntry<String, Integer>> actual = d.iterator();

        while (expected.hasNext()) {
            assertTrue("Iterator hasNext() failed when expected", actual
                    .hasNext());

            String expectedCat = expected.next();
            DictionaryEntry<String, Integer> actualCat = actual.next();

            assertEquals("Iterator next() returned the wrong element",
                    expectedCat, actualCat.getKey());

        }
        assertFalse("Iterator hasNext() failed at the end of the dictionary",
                actual.hasNext());

    }

    @Test(expected = ConcurrentModificationException.class)
    public void testListIteratorConcurrent() {
        List<String> cats = Arrays.asList("Bustopher", "Gus", "Skimbleshanks");

        for (int i = 0; i < cats.size(); i++) {
            d.put(cats.get(i), i);
        }

        Iterator<DictionaryEntry<String, Integer>> it = d.iterator();

        d.remove("Bustopher");

        it.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testListIteratorRemoveUnimplemented() {
        d.iterator().remove();
    }

}
