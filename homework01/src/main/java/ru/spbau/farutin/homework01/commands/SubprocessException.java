package ru.spbau.farutin.homework01.commands;

/**
 * SubprocessException is thrown when there are problems
 * with executing command in subprocess.
 */
public class SubprocessException extends CommandException {
    public SubprocessException(String message) {
        super(message);
    }
}
