package ru.spbau.farutin.homework01.commands.arguments;

import ru.spbau.farutin.homework01.commands.CommandException;

/**
 * IllegalArgumentException is thrown when a command
 * has an argument that it can not have.
 */
public class IllegalArgumentException extends CommandException {
    public IllegalArgumentException(String message) {
        super(message);
    }
}
