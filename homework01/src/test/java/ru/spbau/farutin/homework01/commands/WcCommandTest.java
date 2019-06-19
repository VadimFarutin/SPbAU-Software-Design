package ru.spbau.farutin.homework01.commands;

import org.junit.Test;
import ru.spbau.farutin.homework01.SessionStatus;
import ru.spbau.farutin.homework01.commands.arguments.Argument;
import ru.spbau.farutin.homework01.commands.arguments.ArgumentSource;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * WcCommand tests.
 */
public class WcCommandTest {
    /**
     * Testing on direct user input.
     */
    @Test
    public void testUserInput() throws Exception {
        String resourcePath = "/ru/spbau/farutin/homework01/example.txt";
        String path = Paths.get(getClass().getResource(resourcePath).toURI()).toString();
        String contentStats = "1 3 17";
        Argument argument = mock(Argument.class);
        when(argument.getValue()).thenReturn(path);
        when(argument.getArgumentSource()).thenReturn(ArgumentSource.USER);

        List<Argument> arguments = Collections.singletonList(argument);

        WcCommand command = new WcCommand(arguments);
        CommandOutput output = command.execute();

        assertTrue(output.getValue().isPresent());
        output.getValue().ifPresent(v -> assertThat(v, is(contentStats)));
        assertThat(output.getStatus(), is(SessionStatus.PROCEED));
    }

    /**
     * Testing on argument passed from pipe.
     */
    @Test
    public void testCommandInput() throws Exception {
        String input = "Some example text";
        String contentStats = "1 3 17";
        Argument argument = mock(Argument.class);
        when(argument.getValue()).thenReturn(input);
        when(argument.getArgumentSource()).thenReturn(ArgumentSource.PIPE);

        List<Argument> arguments = Collections.singletonList(argument);

        WcCommand command = new WcCommand(arguments);
        CommandOutput output = command.execute();

        assertTrue(output.getValue().isPresent());
        output.getValue().ifPresent(v -> assertThat(v, is(contentStats)));
        assertThat(output.getStatus(), is(SessionStatus.PROCEED));
    }

    /**
     * Testing on non existing file.
     */
    @Test(expected = CommandException.class)
    public void testIOException() throws Exception {
        String path = "wrong_path";
        Argument argument = mock(Argument.class);
        when(argument.getValue()).thenReturn(path);
        when(argument.getArgumentSource()).thenReturn(ArgumentSource.USER);

        List<Argument> arguments = Collections.singletonList(argument);

        WcCommand command = new WcCommand(arguments);
        command.execute();
    }
}
