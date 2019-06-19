package ru.spbau.farutin.homework01.commands.factory;

import ru.spbau.farutin.homework01.commands.arguments.Argument;
import ru.spbau.farutin.homework01.commands.Command;

import java.util.List;
import java.util.Optional;

/**
 * Factory for commands.
 */
public interface CommandFactory {
    /**
     * Builds certain command.
     * @param name command name
     * @param arguments command arguments
     * @param prevCommand command piped before new command
     * @return built command
     */
    Command buildCommand(String name, List<Argument> arguments, Optional<Command> prevCommand);
}
