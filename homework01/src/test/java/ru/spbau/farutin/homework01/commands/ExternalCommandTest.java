package ru.spbau.farutin.homework01.commands;

import org.junit.Test;
import ru.spbau.farutin.homework01.SessionStatus;
import ru.spbau.farutin.homework01.commands.arguments.Argument;
import ru.spbau.farutin.homework01.environment.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * External command tests.
 */
public class ExternalCommandTest {
    /**
     * Testing on existing command.
     */
    @Test
    public void testExecuteExisting() throws Exception {
        String name;
        List<Argument> arguments;

        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            name = "cmd.exe";
            Argument argument1 = mock(Argument.class);
            when(argument1.getValue()).thenReturn("/c");
            Argument argument2 = mock(Argument.class);
            when(argument2.getValue()).thenReturn("echo");
            Argument argument3 = mock(Argument.class);
            when(argument3.getValue()).thenReturn("hello");

            arguments = Arrays.asList(argument1, argument2, argument3);
        } else {
            name = "echo";
            Argument argument = mock(Argument.class);
            when(argument.getValue()).thenReturn("hello");

            arguments = Collections.singletonList(argument);
        }

        Environment environment = mock(Environment.class);

        ExternalCommand command = new ExternalCommand(name, arguments, environment);
        CommandOutput output = command.execute();

        assertTrue(output.getValue().isPresent());
        output.getValue().ifPresent(v -> assertThat(v, is( "hello" + System.lineSeparator())));
        assertThat(output.getStatus(), is(SessionStatus.PROCEED));
    }

    /**
     * Testing on unknown command.
     */
    @Test(expected = CommandException.class)
    public void testExecuteUnknown() throws Exception {
        String name = "echo1";
        List<Argument> arguments = Collections.EMPTY_LIST;
        Environment environment = mock(Environment.class);

        ExternalCommand command = new ExternalCommand(name, arguments, environment);
        command.execute();
    }
}
