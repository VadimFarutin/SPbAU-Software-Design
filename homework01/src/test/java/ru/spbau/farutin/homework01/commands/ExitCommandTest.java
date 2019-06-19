package ru.spbau.farutin.homework01.commands;

import org.junit.Test;
import ru.spbau.farutin.homework01.SessionStatus;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * ExitCommand tests.
 */
public class ExitCommandTest {
    @Test
    public void testExecute() throws Exception {
        ExitCommand exitCommand = new ExitCommand(Collections.EMPTY_LIST);
        CommandOutput output = exitCommand.execute();

        assertFalse(output.getValue().isPresent());
        assertThat(output.getStatus(), is(SessionStatus.EXIT));
    }
}
