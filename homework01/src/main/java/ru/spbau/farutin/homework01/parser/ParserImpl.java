package ru.spbau.farutin.homework01.parser;

import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.homework01.environment.Environment;
import ru.spbau.farutin.homework01.commands.arguments.Argument;
import ru.spbau.farutin.homework01.commands.Command;
import ru.spbau.farutin.homework01.commands.factory.CommandFactory;
import ru.spbau.farutin.homework01.commands.arguments.StringArgument;

import java.util.*;

/**
 * Parser implementation, supports variables and quoting.
 */
public class ParserImpl implements Parser {
    private Environment environment;
    private CommandFactory commandFactory;

    /**
     * ParserImpl instance constructor.
     * @param environment environment to store variables
     * @param commandFactory command factory to build parsed commands
     */
    public ParserImpl(@NotNull Environment environment, @NotNull CommandFactory commandFactory) {
        this.environment = environment;
        this.commandFactory = commandFactory;
    }

    @Override
    public @NotNull Command parse(@NotNull String line) throws ParserException {
        String substituted = substituteVariables(line);

        Queue<Character> queue = new LinkedList<>();
        for (Character c : substituted.toCharArray()) {
            queue.add(c);
        }

        Set<Character> variableValueDelimiters = Collections.emptySet();
        Set<Character> commandArgumentDelimiters = new HashSet<>(Collections.singletonList(' '));
        Set<Character> commandNameDelimiters = new HashSet<>(Arrays.asList(' ', '='));

        Optional<Command> lastCommand = Optional.empty();
        List<Argument> arguments = new ArrayList<>();
        String name = null;
        ParserState state = ParserState.READING_NAME;

        while (!queue.isEmpty()) {
            Character c = queue.peek();

            if (c == ' ') {
                readSpace(queue);
                continue;
            }

            if (state == ParserState.READING_NAME) {
                name = readCommandToken(queue, commandNameDelimiters);
                state = ParserState.READING_ARGUMENTS;
                continue;
            }

            if (c == '=') {
                queue.poll();
                StringArgument key = new StringArgument(name);
                StringArgument value = new StringArgument(
                        readVariableToken(queue, variableValueDelimiters));
                arguments.add(key);
                arguments.add(value);
                name = "=";
            } else if (c == '|') {
                queue.poll();
                lastCommand = Optional.of(makeCommand(name, arguments, lastCommand));
                arguments = new ArrayList<>();
                state = ParserState.READING_NAME;
            } else {
                StringArgument argument = new StringArgument(
                        readCommandToken(queue, commandArgumentDelimiters));
                arguments.add(argument);
            }
        }

        if (state == ParserState.READING_NAME) {
            throw new ParserException("Missing command name");
        }

        return makeCommand(name, arguments, lastCommand);
    }

    private String substituteVariables(String line) throws ParserException {
        Queue<Character> queue = new LinkedList<>();
        for (Character c : line.toCharArray()) {
            queue.add(c);
        }

        Set<Character> variableNameDelimiters = new HashSet<>(Arrays.asList(' ', '$', '\"'));
        StringBuilder stringBuilder = new StringBuilder();

        while (!queue.isEmpty()) {
            Character c = queue.peek();

            if (c == '\'') {
                stringBuilder.append('\'')
                             .append(readQuotesContent(queue, '\''))
                             .append('\'');
            } else if (c == '$') {
                queue.poll();
                String variable = readVariableToken(queue, variableNameDelimiters);
                Optional<String> value = environment.get(variable);
                String stringValue = value.orElseThrow(
                        () -> new ParserException(String.format("Unknown variable name %s", variable)));
                stringBuilder.append(stringValue);
            } else {
                stringBuilder.append(readCharacter(queue));
            }
        }

        return stringBuilder.toString();
    }

    private void readSpace(Queue<Character> queue) {
        queue.poll();
    }

    private String readQuotesContent(Queue<Character> queue, char quote) throws ParserException {
        queue.poll();
        StringBuilder stringBuilder = new StringBuilder();
        boolean pairFound = false;

        while (!queue.isEmpty()) {
            Character c = queue.poll();

            if (c == quote) {
                pairFound = true;
                break;
            }

            stringBuilder.append(c);
        }

        if (!pairFound) {
            throw new ParserException("Missing pair quote");
        }

        return stringBuilder.toString();
    }

    private String readVariableToken(Queue<Character> queue, Set<Character> delimiters) {
        StringBuilder stringBuilder = new StringBuilder();

        while (!queue.isEmpty()) {
            Character c = queue.peek();

            if (delimiters.contains(c)) {
                break;
            }

            stringBuilder.append(c);
            queue.poll();
        }

        return stringBuilder.toString();
    }

    private String readCommandToken(Queue<Character> queue, Set<Character> delimiters) throws ParserException {
        StringBuilder stringBuilder = new StringBuilder();

        while (!queue.isEmpty()) {
            Character c = queue.peek();

            if (delimiters.contains(c)) {
                break;
            }

            if (c == '\'') {
                stringBuilder.append(readQuotesContent(queue, '\''));
            } else if (c == '\"') {
                stringBuilder.append(readQuotesContent(queue, '\"'));
            } else {
                stringBuilder.append(readCharacter(queue));
            }
        }

        return stringBuilder.toString();
    }

    private Character readCharacter(Queue<Character> queue) {
        return queue.poll();
    }

    private Command makeCommand(String name, List<Argument> arguments, Optional<Command> prevCommand) {
        return commandFactory.buildCommand(name, arguments, prevCommand);
    }

    private enum ParserState {
        READING_NAME,
        READING_ARGUMENTS
    }
}
