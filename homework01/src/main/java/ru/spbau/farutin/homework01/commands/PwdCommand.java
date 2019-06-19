package ru.spbau.farutin.homework01.commands;

import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.homework01.SessionStatus;
import ru.spbau.farutin.homework01.commands.arguments.Argument;

import java.nio.file.Paths;
import java.util.List;

/**
 * pwd shows current CLI path.
 */
public class PwdCommand implements Command {
    public PwdCommand(@NotNull List<Argument> arguments) {
    }

    @Override
    public @NotNull CommandOutput execute() {
        String result = Paths.get("").toAbsolutePath().toString();
        return new CommandOutput(result, SessionStatus.PROCEED);
    }
}
