package ru.spbau.farutin.homework01;

import org.junit.Test;
import ru.spbau.farutin.homework01.commands.Command;
import ru.spbau.farutin.homework01.commands.CommandOutput;
import ru.spbau.farutin.homework01.parser.Parser;
import ru.spbau.farutin.homework01.parser.ParserException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * CLI tests.
 */
public class CLITest {
    /**
     * Tests printing to output.
     */
    @Test
    public void testOutput() throws Exception {
        String input = "echo 42\nexit";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(byteOutputStream);

        Command exit = mock(Command.class);
        when(exit.execute()).thenReturn(new CommandOutput(null, SessionStatus.EXIT));
        Command echo = mock(Command.class);
        when(echo.execute()).thenReturn(new CommandOutput("42", SessionStatus.PROCEED));
        Parser parser = mock(Parser.class);
        when(parser.parse("echo 42")).thenReturn(echo);
        when(parser.parse("exit")).thenReturn(exit);

        CLI cli = new CLI(inputStream, outputStream, outputStream, parser);
        cli.run();

        String output = byteOutputStream.toString();
        assertThat(output, is("> 42\r\n> "));
    }

    /**
     * Tests printing errors.
     */
    @Test
    public void testError() throws Exception {
        String input = "echo $x\nexit";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(byteOutputStream);

        String message = "exception";
        Command exit = mock(Command.class);
        when(exit.execute()).thenReturn(new CommandOutput(null, SessionStatus.EXIT));
        Parser parser = mock(Parser.class);
        when(parser.parse("echo $x")).thenThrow(new ParserException(message));
        when(parser.parse("exit")).thenReturn(exit);

        CLI cli = new CLI(inputStream, outputStream, outputStream, parser);
        cli.run();

        String output = byteOutputStream.toString();
        assertThat(output, is("> exception\r\n> "));
    }
}
