package ru.spbau.farutin.homework01.commands;

import org.junit.Test;
import ru.spbau.farutin.homework01.SessionStatus;
import ru.spbau.farutin.homework01.commands.arguments.Argument;
import ru.spbau.farutin.homework01.commands.arguments.ArgumentSource;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * GrepCommand tests.
 */
public class GrepCommandTest {
    public static Argument mockArgument(String value, ArgumentSource source) throws Exception  {
        Argument argument = mock(Argument.class);
        when(argument.getValue()).thenReturn(value);
        when(argument.getArgumentSource()).thenReturn(source);
        return argument;
    }

    public static Argument mockUserArgument(String value) throws Exception  {
        return mockArgument(value, ArgumentSource.USER);
    }

    public static Argument mockPipeArgument(String value) throws Exception  {
        return mockArgument(value, ArgumentSource.PIPE);
    }

    /**
     * Testing on direct user input.
     */
    @Test
    public void testUserInputEmpty() throws Exception {
        String resourcePath = "/ru/spbau/farutin/homework01/example.txt";
        String path = Paths.get(getClass().getResource(resourcePath).toURI()).toString();
        String regex = "some";
        String expected = "";

        List<Argument> arguments = Arrays.asList(
                mockUserArgument(regex),
                mockUserArgument(path));

        GrepCommand command = new GrepCommand(arguments);
        CommandOutput output = command.execute();

        assertTrue(output.getValue().isPresent());
        output.getValue().ifPresent(v -> assertThat(v, is(expected)));
        assertThat(output.getStatus(), is(SessionStatus.PROCEED));
    }

    /**
     * Testing on direct user input.
     */
    @Test
    public void testUserInput() throws Exception {
        String resourcePath = "/ru/spbau/farutin/homework01/example.txt";
        String path = Paths.get(getClass().getResource(resourcePath).toURI()).toString();
        String regex = "some";
        String expected = "Some example text";

        List<Argument> arguments = Arrays.asList(
                mockUserArgument(regex),
                mockUserArgument(path),
                mockUserArgument("-i"));

        GrepCommand command = new GrepCommand(arguments);
        CommandOutput output = command.execute();

        assertTrue(output.getValue().isPresent());
        output.getValue().ifPresent(v -> assertThat(v, is(expected)));
        assertThat(output.getStatus(), is(SessionStatus.PROCEED));
    }

    /**
     * Testing without ignore case flag.
     */
    @Test
    public void testNoIgnoreCase() throws Exception {
        String data = "hello" + System.lineSeparator() + "hallo" + System.lineSeparator() + "hi, world!" + System.lineSeparator() + "heLlO" + System.lineSeparator() + "hell0";
        String regex = "heLlO";
        String expected = "heLlO";

        List<Argument> arguments = Arrays.asList(
                mockUserArgument(regex),
                mockPipeArgument(data));

        GrepCommand command = new GrepCommand(arguments);
        CommandOutput output = command.execute();

        assertTrue(output.getValue().isPresent());
        output.getValue().ifPresent(v -> assertThat(v, is(expected)));
        assertThat(output.getStatus(), is(SessionStatus.PROCEED));
    }

    /**
     * Testing with ignore case flag.
     */
    @Test
    public void testIgnoreCase() throws Exception {
        String data = "hello" + System.lineSeparator() + "hallo" + System.lineSeparator() + "hi, world!" + System.lineSeparator() + "heLlO" + System.lineSeparator() + "hell0";
        String regex = "heLlO";
        String expected = "hello" + System.lineSeparator() + "heLlO";

        List<Argument> arguments = Arrays.asList(
                mockUserArgument(regex),
                mockUserArgument("-i"),
                mockPipeArgument(data));

        GrepCommand command = new GrepCommand(arguments);
        CommandOutput output = command.execute();

        assertTrue(output.getValue().isPresent());
        output.getValue().ifPresent(v -> assertThat(v, is(expected)));
        assertThat(output.getStatus(), is(SessionStatus.PROCEED));
    }

    /**
     * Testing without word flag.
     */
    @Test
    public void testNoWholeWord() throws Exception {
        String data = "hello" + System.lineSeparator() + "hellooo" + System.lineSeparator() + "hi, world!" + System.lineSeparator() + "heLlO" + System.lineSeparator() + "hell0 hello !";
        String regex = "hello";
        String expected = "hello" + System.lineSeparator() + "hellooo" + System.lineSeparator() + "hell0 hello !";

        List<Argument> arguments = Arrays.asList(
                mockUserArgument(regex),
                mockPipeArgument(data));

        GrepCommand command = new GrepCommand(arguments);
        CommandOutput output = command.execute();

        assertTrue(output.getValue().isPresent());
        output.getValue().ifPresent(v -> assertThat(v, is(expected)));
        assertThat(output.getStatus(), is(SessionStatus.PROCEED));
    }

    /**
     * Testing with word flag.
     */
    @Test
    public void testWholeWord() throws Exception {
        String data = "hello" + System.lineSeparator() + "hellooo" + System.lineSeparator() + "hi, world!" + System.lineSeparator() + "heLlO" + System.lineSeparator() + "hell0 hello !";
        String regex = "hello";
        String expected = "hello" + System.lineSeparator() + "hell0 hello !";

        List<Argument> arguments = Arrays.asList(
                mockUserArgument(regex),
                mockUserArgument("-w"),
                mockPipeArgument(data));

        GrepCommand command = new GrepCommand(arguments);
        CommandOutput output = command.execute();

        assertTrue(output.getValue().isPresent());
        output.getValue().ifPresent(v -> assertThat(v, is(expected)));
        assertThat(output.getStatus(), is(SessionStatus.PROCEED));
    }

    /**
     * Testing with A flag.
     */
    @Test
    public void testFollowingLines() throws Exception {
        String data = "hello" + System.lineSeparator() + "hellnooo" + System.lineSeparator() + "hi, world!" + System.lineSeparator() + "heLlO" + System.lineSeparator() + "" +
                      "hell0 hello !" + System.lineSeparator() + "hello" + System.lineSeparator() + "abc" + System.lineSeparator() + "xyz" + System.lineSeparator() + "" +
                      "hhelloo";
        String regex = "hello";
        String expected = "hello" + System.lineSeparator() + "hellnooo" + System.lineSeparator() + "hi, world!" + System.lineSeparator() + "" +
                          "hell0 hello !" + System.lineSeparator() + "hello" + System.lineSeparator() + "abc" + System.lineSeparator() + "xyz" + System.lineSeparator() + "" +
                          "hhelloo";

        List<Argument> arguments = Arrays.asList(
                mockUserArgument(regex),
                mockUserArgument("-A2"),
                mockPipeArgument(data));

        GrepCommand command = new GrepCommand(arguments);
        CommandOutput output = command.execute();

        assertTrue(output.getValue().isPresent());
        output.getValue().ifPresent(v -> assertThat(v, is(expected)));
        assertThat(output.getStatus(), is(SessionStatus.PROCEED));
    }
}
