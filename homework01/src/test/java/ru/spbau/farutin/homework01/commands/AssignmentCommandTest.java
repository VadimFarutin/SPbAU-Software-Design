package ru.spbau.farutin.homework01.commands;

import org.junit.Test;
import ru.spbau.farutin.homework01.SessionStatus;
import ru.spbau.farutin.homework01.commands.arguments.Argument;
import ru.spbau.farutin.homework01.environment.Environment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * AssignmentCommand tests.
 */
public class AssignmentCommandTest {
    @Test
    public void testExecute() throws Exception {
        String key = "x";
        String value = "42";
        Argument keyArgument = mock(Argument.class);
        when(keyArgument.getValue()).thenReturn(key);
        Argument valueArgument = mock(Argument.class);
        when(valueArgument.getValue()).thenReturn(value);

        List<Argument> arguments = new ArrayList<>();
        arguments.add(keyArgument);
        arguments.add(valueArgument);

        Environment environment = mock(Environment.class);

        AssignmentCommand command = new AssignmentCommand(arguments, environment);
        CommandOutput output = command.execute();

        verify(environment).set(key, value);
        assertFalse(output.getValue().isPresent());
        assertThat(output.getStatus(), is(SessionStatus.PROCEED));
    }
}
