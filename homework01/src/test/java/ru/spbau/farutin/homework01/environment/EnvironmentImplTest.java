package ru.spbau.farutin.homework01.environment;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * EnvironmentImpl tests.
 */
public class EnvironmentImplTest {
    /**
     * Tests setting value for a new key.
     */
    @Test
    public void testSetNewKey() throws Exception {
        String key = "x";
        String value = "42";
        Environment environment = new EnvironmentImpl();
        environment.set(key, value);

        Optional<String> optionalValue = environment.get(key);

        assertTrue(optionalValue.isPresent());
        optionalValue.ifPresent(v -> assertThat(v, is(value)));
    }

    /**
     * Tests setting value for an existing key.
     */
    @Test
    public void testSetExistingKey() throws Exception {
        String key = "x";
        String value = "42";
        String newValue = "111";
        Environment environment = new EnvironmentImpl();
        environment.set(key, value);
        environment.set(key, newValue);

        Optional<String> optionalValue = environment.get(key);

        assertTrue(optionalValue.isPresent());
        optionalValue.ifPresent(v -> assertThat(v, is(newValue)));
    }

    /**
     * Tests getting value of a non existing key.
     */
    @Test
    public void testGetWrongKey() throws Exception {
        String key = "x";
        String value = "42";
        String wrongKey = "y";
        Environment environment = new EnvironmentImpl();
        environment.set(key, value);

        Optional<String> optionalValue = environment.get(wrongKey);

        assertFalse(optionalValue.isPresent());
    }

    /**
     * Tests getting empty entry list.
     */
    @Test
    public void testGetEntryListEmpty() throws Exception {
        Environment environment = new EnvironmentImpl();

        List<Environment.Entry> entryList = environment.entryList();

        assertThat(entryList.size(), is(0));
    }

    /**
     * Tests getting entry list.
     */
    @Test
    public void testGetEntryList() throws Exception {
        ArrayList<EnvironmentImpl.Entry> keyValueList = new ArrayList<>();
        keyValueList.add(new EnvironmentImpl.Entry("y", "111"));
        keyValueList.add(new EnvironmentImpl.Entry("x", "42"));

        Environment environment = new EnvironmentImpl();
        for (EnvironmentImpl.Entry entry : keyValueList) {
            environment.set(entry.getKey(), entry.getValue());
        }

        Comparator<Environment.Entry> comparator = Comparator.comparing(Environment.Entry::getKey);

        List<Environment.Entry> entryList = environment.entryList();
        keyValueList.sort(comparator);
        entryList.sort(comparator);

        assertThat(entryList.size(), is(keyValueList.size()));
        int i = 0;
        for (Environment.Entry entry : entryList) {
            assertThat(entry.getKey(), is(keyValueList.get(i).getKey()));
            assertThat(entry.getValue(), is(keyValueList.get(i).getValue()));
            i++;
        }
    }
}
