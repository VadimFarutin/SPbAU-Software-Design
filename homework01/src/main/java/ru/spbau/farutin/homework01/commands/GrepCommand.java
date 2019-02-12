package ru.spbau.farutin.homework01.commands;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.homework01.SessionStatus;
import ru.spbau.farutin.homework01.commands.arguments.Argument;
import ru.spbau.farutin.homework01.commands.arguments.ArgumentSource;
import ru.spbau.farutin.homework01.commands.arguments.IllegalArgumentException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * grep finds lines that match a given pattern.
 */
public class GrepCommand implements Command {
    private List<Argument> arguments;

    public GrepCommand(@NotNull List<Argument> arguments) {
        this.arguments = arguments;
    }

    @Override
    public @NotNull CommandOutput execute() throws CommandException {
        ArgumentParser parser = ArgumentParsers.newFor("GrepCommand").build();
        parser.addArgument("regex");
        parser.addArgument("file");
        parser.addArgument("-i").action(Arguments.storeTrue());
        parser.addArgument("-w").action(Arguments.storeTrue());
        parser.addArgument("-A")
                .type(Integer.class)
                .setDefault(0);

        List<String> stringArguments = new ArrayList<>();
        for (Argument argument : arguments) {
            stringArguments.add(argument.getValue());
        }

        String result;

        try {
            Namespace namespace = parser.parseArgs(
                    stringArguments.toArray(new String[stringArguments.size()]));
            List<String> lines;

            if (arguments.get(arguments.size() - 1).getArgumentSource() == ArgumentSource.USER) {
                String path = namespace.getString("file");
                try {
                    lines = Files.readAllLines(Paths.get(path));
                } catch (IOException e) {
                    throw new FileIOException(String.format("Failed to read from file %s", path));
                }
            } else {
                String data = namespace.getString("file");
                lines = Arrays.asList(data.split("\n"));
            }

            List<String> selectedLines = matchLines(lines,
                                                    namespace.getString("regex"),
                                                    namespace.getBoolean("i"),
                                                    namespace.getBoolean("w"),
                                                    namespace.getInt("A"));
            StringJoiner joiner = new StringJoiner("\n");

            for (String line : selectedLines) {
                joiner.add(line);
            }

            result = joiner.toString();
        } catch (ArgumentParserException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return new CommandOutput(result, SessionStatus.PROCEED);
    }

    private @NotNull List<String> matchLines(@NotNull List<String> lines,
                                             @NotNull String regex,
                                             boolean caseInsensitive,
                                             boolean searchWholeWord,
                                             int followingLinesNumber) {
        List<String> selectedLines = new ArrayList<>();
        int patternFlag = caseInsensitive ? Pattern.CASE_INSENSITIVE : 0;
        Pattern pattern = Pattern.compile(regex, patternFlag);
        int linesToTake = 0;

        for (String line : lines) {
            boolean matched = searchWholeWord
                              ? containsWord(line, pattern)
                              : containsSubstring(line, pattern);

            if (matched) {
                selectedLines.add(line);
                linesToTake = followingLinesNumber;
            } else if (linesToTake > 0) {
                selectedLines.add(line);
                linesToTake--;
            }
        }

        return selectedLines;
    }

    private boolean containsSubstring(@NotNull String line, @NotNull Pattern pattern) {
        return pattern.matcher(line).find();
    }

    private boolean containsWord(@NotNull String line, @NotNull Pattern pattern) {
        String[] words = line.split("\\s+");

        for (String word : words) {
            if (pattern.matcher(word).matches()) {
                return true;
            }
        }

        return false;
    }
}
