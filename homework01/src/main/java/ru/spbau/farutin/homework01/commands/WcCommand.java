package ru.spbau.farutin.homework01.commands;

import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.homework01.SessionStatus;
import ru.spbau.farutin.homework01.commands.arguments.Argument;
import ru.spbau.farutin.homework01.commands.arguments.ArgumentSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * wc counts number of lines, words and bytes of its input.
 */
public class WcCommand implements Command {
    private List<Argument> arguments;

    public WcCommand(@NotNull List<Argument> arguments) {
        this.arguments = arguments;
    }

    @Override
    public @NotNull CommandOutput execute() throws CommandException {
        String data;
        Argument argument = arguments.get(0);

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

        int linesCnt = data.split("\n").length;
        int wordsCnt = data.trim().split("\\s+").length;
        int bytesCnt = data.getBytes().length;
        String result = String.format("%d %d %d", linesCnt, wordsCnt, bytesCnt);

        return new CommandOutput(result, SessionStatus.PROCEED);
    }
}
