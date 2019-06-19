package ru.spbau.farutin.homework01.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.farutin.homework01.SessionStatus;

import java.util.Optional;

/**
 * Class to store command output.
 */
public class CommandOutput {
    private String value;
    private SessionStatus status;

    /**
     * @param value command result value
     * @param status CLI status after executing this command
     */
    public CommandOutput(@Nullable String value, @NotNull SessionStatus status) {
        this.value = value;
        this.status = status;
    }

    public @NotNull Optional<String> getValue() {
        return Optional.ofNullable(value);
    }

    public SessionStatus getStatus() {
        return status;
    }
}
