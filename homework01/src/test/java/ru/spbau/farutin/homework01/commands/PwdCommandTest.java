package ru.spbau.farutin.homework01.commands;

import org.junit.Test;
import ru.spbau.farutin.homework01.SessionStatus;

import java.nio.file.Paths;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * PwdCommand tests.
 */
public class PwdCommandTest {
    @Test
    public void testExecute() throws Exception {
        String currentPath = Paths.get("").toAbsolutePath().toString();

        PwdCommand command = new PwdCommand(Collections.EMPTY_LIST);
        CommandOutput output = command.execute();

        assertTrue(output.getValue().isPresent());
        output.getValue().ifPresent(v -> assertThat(v, is(currentPath)));
        assertThat(output.getStatus(), is(SessionStatus.PROCEED));
    }
}
