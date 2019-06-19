package ru.spbau.farutin.homework01.commands.arguments;

import org.jetbrains.annotations.NotNull;

/**
 * Simple string argument.
 */
public class StringArgument implements Argument {
    private String stringValue;

    public StringArgument(@NotNull String value) {
        stringValue = value;
    }

    @Override
    public @NotNull String getValue() {
        return stringValue;
    }

    @Override
    public @NotNull ArgumentSource getArgumentSource() {
        return ArgumentSource.USER;
    }
}
