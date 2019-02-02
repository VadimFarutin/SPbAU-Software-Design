package ru.spbau.farutin.homework01.commands;

/**
 * Command that can be executed.
 */
public interface Command {
    /**
     * Executes command.
     * @return command results
     * @throws CommandException if failed to execute
     */
    CommandOutput execute() throws CommandException;
}
