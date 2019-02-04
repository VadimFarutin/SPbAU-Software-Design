package ru.spbau.farutin.homework01.commands;

import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.homework01.SessionStatus;
import ru.spbau.farutin.homework01.commands.arguments.Argument;

import java.util.List;
import java.util.StringJoiner;

/**
 * echo prints its arguments.
 */
public class EchoCommand implements Command {
    private List<Argument> arguments;

    public EchoCommand(@NotNull List<Argument> arguments) {
        this.arguments = arguments;
    }

    @Override
    public @NotNull CommandOutput execute() throws CommandException {
        StringJoiner joiner = new StringJoiner(" ");

        for (Argument argument : arguments) {
            joiner.add(argument.getValue());
        }

        String data = joiner.toString();
        return new CommandOutput(data, SessionStatus.PROCEED);
    }
}
