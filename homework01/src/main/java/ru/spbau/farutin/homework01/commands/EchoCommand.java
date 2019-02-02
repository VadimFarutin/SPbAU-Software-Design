package ru.spbau.farutin.homework01.commands;

import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.homework01.SessionStatus;
import ru.spbau.farutin.homework01.commands.arguments.Argument;

import java.util.List;

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
        StringBuilder builder = new StringBuilder();

        for (Argument argument : arguments) {
            builder.append(argument.getValue());
        }

        String data = builder.toString();
        return new CommandOutput(data, SessionStatus.PROCEED);
    }
}
