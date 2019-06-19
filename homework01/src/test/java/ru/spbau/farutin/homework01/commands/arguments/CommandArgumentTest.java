package ru.spbau.farutin.homework01.commands.arguments;

import org.junit.Test;
import ru.spbau.farutin.homework01.SessionStatus;
import ru.spbau.farutin.homework01.commands.Command;
import ru.spbau.farutin.homework01.commands.CommandOutput;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * CommandArgument tests.
 */
public class CommandArgumentTest {
    @Test
    public void testGetValue() throws Exception {
        String value = "hello";
        SessionStatus status = SessionStatus.PROCEED;

        Command command = mock(Command.class);
        when(command.execute()).thenReturn(new CommandOutput(value, status));

        CommandArgument commandArgument = new CommandArgument(command);

        assertThat(commandArgument.getValue(), is(value));
    }

    @Test
    public void testGetArgumentSource() throws Exception {
        String value = "hello";
        SessionStatus status = SessionStatus.PROCEED;

        Command command = mock(Command.class);
        when(command.execute()).thenReturn(new CommandOutput(value, status));

        CommandArgument commandArgument = new CommandArgument(command);

        assertThat(commandArgument.getArgumentSource(), is(ArgumentSource.PIPE));
    }
}
