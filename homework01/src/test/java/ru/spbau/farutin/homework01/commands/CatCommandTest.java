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
 * CatCommand tests.
 */
public class CatCommandTest {
    /**
     * Testing on existing file.
     */
    @Test
    public void testExecute() throws Exception {
        String resourcePath = "/ru/spbau/farutin/homework01/example.txt";
        String path = Paths.get(getClass().getResource(resourcePath).toURI()).toString();
        String content = "Some example text";
        Argument argument = mock(Argument.class);
        when(argument.getValue()).thenReturn(path);
        when(argument.getArgumentSource()).thenReturn(ArgumentSource.USER);

        List<Argument> arguments = Collections.singletonList(argument);

        CatCommand command = new CatCommand(arguments);
        CommandOutput output = command.execute();

        assertTrue(output.getValue().isPresent());
        output.getValue().ifPresent(v -> assertThat(v, is(content)));
        assertThat(output.getStatus(), is(SessionStatus.PROCEED));
    }

    /**
     * Testing on non existing file.
     */
    @Test(expected = CommandException.class)
    public void testIOException() throws Exception {
        String path = "wrong_path";
        Argument keyArgument = mock(Argument.class);
        when(keyArgument.getValue()).thenReturn(path);
        when(keyArgument.getArgumentSource()).thenReturn(ArgumentSource.USER);

        List<Argument> arguments = Collections.singletonList(keyArgument);

        CatCommand command = new CatCommand(arguments);
        command.execute();
    }
}
