package ru.spbau.farutin.homework01.commands;

import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.homework01.SessionStatus;
import ru.spbau.farutin.homework01.commands.arguments.Argument;

import java.util.List;

/**
 * exit finishes CLI session.
 */
public class ExitCommand implements Command {
    public ExitCommand(@NotNull List<Argument> arguments) {
    }

    @Override
    public @NotNull CommandOutput execute() {
        return new CommandOutput(null, SessionStatus.EXIT);
    }
}
