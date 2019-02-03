package ru.spbau.farutin.homework01.parser;

import org.junit.Test;
import ru.spbau.farutin.homework01.SessionStatus;
import ru.spbau.farutin.homework01.commands.Command;
import ru.spbau.farutin.homework01.commands.CommandOutput;
import ru.spbau.farutin.homework01.commands.factory.CommandFactory;
import ru.spbau.farutin.homework01.environment.Environment;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Parser tests.
 */
public class ParserImplTest {
    /**
     * Tests parsing exit command.
     */
    @Test
    public void testParseExit() throws Exception {
        Environment environment = mock(Environment.class);

        Command exit = mock(Command.class);
        when(exit.execute()).thenReturn(new CommandOutput(null, SessionStatus.EXIT));

        CommandFactory commandFactory = mock(CommandFactory.class);
        when(commandFactory.buildCommand(eq("exit"), any(), any()))
                .thenReturn(exit);

        Parser parser = new ParserImpl(environment, commandFactory);
        Command parserResult = parser.parse("exit");
        CommandOutput commandResult = parserResult.execute();

        assertFalse(commandResult.getValue().isPresent());
        assertThat(commandResult.getStatus(), is(SessionStatus.EXIT));
    }

    /**
     * Tests parsing cat command.
     */
    @Test
    public void testParseCat() throws Exception {
        Environment environment = mock(Environment.class);
        String output = "hello";

        Command cat = mock(Command.class);
        when(cat.execute()).thenReturn(new CommandOutput(output, SessionStatus.PROCEED));

        CommandFactory commandFactory = mock(CommandFactory.class);
        when(commandFactory.buildCommand(eq("cat"), any(), any()))
                .thenReturn(cat);

        Parser parser = new ParserImpl(environment, commandFactory);
        Command parserResult = parser.parse("cat hello.txt");
        CommandOutput commandResult = parserResult.execute();

        assertTrue(commandResult.getValue().isPresent());
        commandResult.getValue().ifPresent(value -> assertThat(value, is(output)));
        assertThat(commandResult.getStatus(), is(SessionStatus.PROCEED));
    }

    /**
     * Tests parsing wc command.
     */
    @Test
    public void testParseWc() throws Exception {
        Environment environment = mock(Environment.class);
        String output = "1 1 5";

        Command wc = mock(Command.class);
        when(wc.execute()).thenReturn(new CommandOutput(output, SessionStatus.PROCEED));

        CommandFactory commandFactory = mock(CommandFactory.class);
        when(commandFactory.buildCommand(eq("wc"), any(), any()))
                .thenReturn(wc);

        Parser parser = new ParserImpl(environment, commandFactory);
        Command parserResult = parser.parse("wc hello.txt");
        CommandOutput commandResult = parserResult.execute();

        assertTrue(commandResult.getValue().isPresent());
        commandResult.getValue().ifPresent(value -> assertThat(value, is(output)));
        assertThat(commandResult.getStatus(), is(SessionStatus.PROCEED));
    }

    /**
     * Tests parsing echo command.
     */
    @Test
    public void testParseEcho() throws Exception {
        Environment environment = mock(Environment.class);
        String output = "hello";

        Command echo = mock(Command.class);
        when(echo.execute()).thenReturn(new CommandOutput(output, SessionStatus.PROCEED));

        CommandFactory commandFactory = mock(CommandFactory.class);
        when(commandFactory.buildCommand(eq("echo"), any(), any()))
                .thenReturn(echo);

        Parser parser = new ParserImpl(environment, commandFactory);
        Command parserResult = parser.parse("echo " + output);
        CommandOutput commandResult = parserResult.execute();

        assertTrue(commandResult.getValue().isPresent());
        commandResult.getValue().ifPresent(value -> assertThat(value, is(output)));
        assertThat(commandResult.getStatus(), is(SessionStatus.PROCEED));
    }

    /**
     * Tests parsing pwd command.
     */
    @Test
    public void testParsePwd() throws Exception {
        Environment environment = mock(Environment.class);
        String output = "./hello";

        Command pwd = mock(Command.class);
        when(pwd.execute()).thenReturn(new CommandOutput(output, SessionStatus.PROCEED));

        CommandFactory commandFactory = mock(CommandFactory.class);
        when(commandFactory.buildCommand(eq("pwd"), any(), any()))
                .thenReturn(pwd);

        Parser parser = new ParserImpl(environment, commandFactory);
        Command parserResult = parser.parse("pwd");
        CommandOutput commandResult = parserResult.execute();

        assertTrue(commandResult.getValue().isPresent());
        commandResult.getValue().ifPresent(value -> assertThat(value, is(output)));
        assertThat(commandResult.getStatus(), is(SessionStatus.PROCEED));
    }

    /**
     * Tests parsing assignment command.
     */
    @Test
    public void testParseAssignment() throws Exception {
        Environment environment = mock(Environment.class);

        Command assignment = mock(Command.class);
        when(assignment.execute()).thenReturn(new CommandOutput(null, SessionStatus.PROCEED));

        CommandFactory commandFactory = mock(CommandFactory.class);
        when(commandFactory.buildCommand(eq("="), any(), any()))
                .thenReturn(assignment);

        Parser parser = new ParserImpl(environment, commandFactory);
        Command parserResult = parser.parse("x=42");
        CommandOutput commandResult = parserResult.execute();

        assertFalse(commandResult.getValue().isPresent());
        assertThat(commandResult.getStatus(), is(SessionStatus.PROCEED));
    }

    /**
     * Tests parsing external command.
     */
    @Test
    public void testParseExternal() throws Exception {
        Environment environment = mock(Environment.class);
        String output = "./hello";

        Command ls = mock(Command.class);
        when(ls.execute()).thenReturn(new CommandOutput(output, SessionStatus.PROCEED));

        CommandFactory commandFactory = mock(CommandFactory.class);
        when(commandFactory.buildCommand(eq("ls"), any(), any()))
                .thenReturn(ls);

        Parser parser = new ParserImpl(environment, commandFactory);
        Command parserResult = parser.parse("ls");
        CommandOutput commandResult = parserResult.execute();

        assertTrue(commandResult.getValue().isPresent());
        commandResult.getValue().ifPresent(value -> assertThat(value, is(output)));
        assertThat(commandResult.getStatus(), is(SessionStatus.PROCEED));
    }
}
