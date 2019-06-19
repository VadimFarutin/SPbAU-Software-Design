package ru.spbau.farutin.homework01.commands;

import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.homework01.*;
import ru.spbau.farutin.homework01.commands.arguments.Argument;
import ru.spbau.farutin.homework01.commands.arguments.IllegalArgumentException;
import ru.spbau.farutin.homework01.environment.Environment;

import java.util.List;

/**
 * AssignmentCommand performs variable assignment in given environment.
 */
public class AssignmentCommand implements Command {
    private List<Argument> arguments;
    private Environment environment;

    public AssignmentCommand(@NotNull List<Argument> arguments, @NotNull Environment environment) {
        this.arguments = arguments;
        this.environment = environment;
    }

    @Override
    public @NotNull CommandOutput execute() throws CommandException {
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("assignment: 2 arguments expected");
        }

        String key = arguments.get(0).getValue();
        String value = arguments.get(1).getValue();
        environment.set(key, value);
        return new CommandOutput(null, SessionStatus.PROCEED);
    }
}
