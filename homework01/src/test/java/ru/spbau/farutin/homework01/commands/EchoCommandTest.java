package ru.spbau.farutin.homework01.commands;

import org.junit.Test;
import ru.spbau.farutin.homework01.SessionStatus;
import ru.spbau.farutin.homework01.commands.arguments.Argument;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * EchoCommand tests.
 */
public class EchoCommandTest {
    /**
     * Testing on one argument.
     */
    @Test
    public void testExecuteOneArgument() throws Exception {
        String value = "hello";
        Argument argument = mock(Argument.class);
        when(argument.getValue()).thenReturn(value);

        List<Argument> arguments = new ArrayList<>();
        arguments.add(argument);

        EchoCommand command = new EchoCommand(arguments);
        CommandOutput output = command.execute();

        assertTrue(output.getValue().isPresent());
        output.getValue().ifPresent(v -> assertThat(v, is(value)));
        assertThat(output.getStatus(), is(SessionStatus.PROCEED));
    }

    /**
     * Testing on two arguments.
     */
    @Test
    public void testExecuteTwoArgument() throws Exception {
        String first = "hello";
        Argument firstArgument = mock(Argument.class);
        when(firstArgument.getValue()).thenReturn(first);
        String second = "hello";
        Argument secondArgument = mock(Argument.class);
        when(secondArgument.getValue()).thenReturn(second);

        List<Argument> arguments = new ArrayList<>();
        arguments.add(firstArgument);
        arguments.add(secondArgument);

        EchoCommand command = new EchoCommand(arguments);
        CommandOutput output = command.execute();

        assertTrue(output.getValue().isPresent());
        output.getValue().ifPresent(v -> assertThat(v, is(first + " " + second)));
        assertThat(output.getStatus(), is(SessionStatus.PROCEED));
    }
}
