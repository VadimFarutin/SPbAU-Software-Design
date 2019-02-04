package ru.spbau.farutin.homework01.commands;

import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.homework01.SessionStatus;
import ru.spbau.farutin.homework01.commands.arguments.Argument;
import ru.spbau.farutin.homework01.commands.arguments.IllegalArgumentException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringJoiner;

/**
 * cat prints file content.
 */
public class CatCommand implements Command {
    private List<Argument> arguments;

    public CatCommand(@NotNull List<Argument> arguments) {
        this.arguments = arguments;
    }

    @Override
    public @NotNull CommandOutput execute() throws CommandException {
        StringJoiner joiner = new StringJoiner("\n");

        for (Argument argument : arguments) {
            String path = argument.getValue();
            try {
                String data = new String(Files.readAllBytes(Paths.get(path)));
                joiner.add(data);
            } catch (IOException e) {
                throw new FileIOException(String.format("Failed to read from file %s.", path));
            }
        }

        return new CommandOutput(joiner.toString(), SessionStatus.PROCEED);
    }
}
