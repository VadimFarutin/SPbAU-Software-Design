package ru.spbau.farutin.homework01.commands;

/**
 * FileIOException is thrown when there are problems
 * with working with system file.
 */
public class FileIOException extends CommandException {
    public FileIOException(String message) {
        super(message);
    }
}
