package ru.spbau.farutin.homework01.commands.arguments;

import ru.spbau.farutin.homework01.commands.CommandException;
import ru.spbau.farutin.homework01.commands.FileIOException;
import ru.spbau.farutin.homework01.commands.SubprocessException;

/**
 * Command argument.
 */
public interface Argument {
    String getValue() throws CommandException;

    ArgumentSource getArgumentSource();
}
