package ru.spbau.farutin.homework01;

import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.homework01.commands.*;
import ru.spbau.farutin.homework01.commands.arguments.IllegalArgumentException;
import ru.spbau.farutin.homework01.commands.factory.CommandFactory;
import ru.spbau.farutin.homework01.commands.factory.CommandFactoryImpl;
import ru.spbau.farutin.homework01.environment.Environment;
import ru.spbau.farutin.homework01.environment.EnvironmentImpl;
import ru.spbau.farutin.homework01.parser.Parser;
import ru.spbau.farutin.homework01.parser.ParserException;
import ru.spbau.farutin.homework01.parser.ParserImpl;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;

/**
 * Simple CLI.
 */
public class CLI {
    private Scanner scanner;
    private PrintStream outputStream;
    private PrintStream errorStream;
    private Parser parser;

    public static void main(String[] args) {
        Environment environment = new EnvironmentImpl();
        CommandFactory commandFactory = new CommandFactoryImpl(environment);
        Parser parser = new ParserImpl(environment, commandFactory);

        CLI cli = new CLI(System.in, System.out, System.out, parser);
        cli.run();
    }

    /**
     * Makes new CLI instance.
     * @param inputStream input of CLI
     * @param outputStream output of CLI
     * @param errorStream stream to print errors
     * @param parser parser to parse lines
     */
    public CLI(@NotNull InputStream inputStream,
               @NotNull PrintStream outputStream,
               @NotNull PrintStream errorStream,
               @NotNull Parser parser) {
        scanner = new Scanner(inputStream);
        this.outputStream = outputStream;
        this.errorStream = errorStream;
        this.parser = parser;
    }

    /**
     * Starts new session.
     */
    public void run() {
        SessionStatus sessionStatus = SessionStatus.PROCEED;

        while (sessionStatus == SessionStatus.PROCEED) {
            try {
                String line = read();
                Command command = parser.parse(line);
                CommandOutput result = command.execute();
                Optional<String> value = result.getValue();
                value.ifPresent(this::printOutput);
                sessionStatus = result.getStatus();
            } catch (ParserException | CommandException e) {
                printError(e.getMessage());
            }
        }
    }

    private @NotNull String read() {
        outputStream.print("> ");
        return scanner.nextLine();
    }

    private void printOutput(@NotNull String result) {
        outputStream.println(result);
    }

    private void printError(@NotNull String message) {
        errorStream.println(message);
    }
}
