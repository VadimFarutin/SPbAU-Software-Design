package ru.spbau.farutin.homework01.commands;

import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.homework01.SessionStatus;
import ru.spbau.farutin.homework01.commands.arguments.Argument;
import ru.spbau.farutin.homework01.commands.arguments.ArgumentSource;

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
        StringJoiner joiner = new StringJoiner(System.lineSeparator());

        for (Argument argument : arguments) {
            String data;

            if (argument.getArgumentSource() == ArgumentSource.USER) {
                String path = argument.getValue();
                try {
                    data = new String(Files.readAllBytes(Paths.get(path)));
                } catch (IOException e) {
                    throw new FileIOException(String.format("Failed to read from file %s", path));
                }
            } else {
                data = argument.getValue();
            }

            joiner.add(data);
        }

        return new CommandOutput(joiner.toString(), SessionStatus.PROCEED);
    }
}
