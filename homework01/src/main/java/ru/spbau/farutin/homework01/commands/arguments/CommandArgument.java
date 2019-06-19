package ru.spbau.farutin.homework01.commands.arguments;

import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.homework01.commands.*;

import java.util.Optional;

/**
 * Command as an argument of another command.
 */
public class CommandArgument implements Argument {
    private Command command;
    private CommandOutput commandOutput = null;

    public CommandArgument(@NotNull Command command) {
        this.command = command;
    }

    @Override
    public @NotNull String getValue() throws CommandException {
        if (commandOutput == null) {
            commandOutput = command.execute();
        }

        Optional<String> value = commandOutput.getValue();
        return value.orElseThrow(
                () -> new IllegalArgumentException("Command argument has no return value."));
    }

    @Override
    public @NotNull ArgumentSource getArgumentSource() {
        return ArgumentSource.PIPE;
    }
}
