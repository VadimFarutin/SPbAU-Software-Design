package ru.spbau.farutin.homework01.commands.arguments;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * StringArgument tests.
 */
public class StringArgumentTest {
    @Test
    public void testGetValue() throws Exception {
        String value = "hello";
        StringArgument stringArgument = new StringArgument(value);

        assertThat(stringArgument.getValue(), is(value));
    }

    @Test
    public void testGetArgumentSource() throws Exception {
        String value = "hello";
        StringArgument stringArgument = new StringArgument(value);

        assertThat(stringArgument.getArgumentSource(), is(ArgumentSource.USER));
    }
}
