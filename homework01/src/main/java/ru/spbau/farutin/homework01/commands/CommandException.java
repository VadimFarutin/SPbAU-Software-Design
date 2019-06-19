package ru.spbau.farutin.homework01.commands;

/**
 * CommandException is thrown when command is executed with errors.
 */
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }
}
