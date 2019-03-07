package ru.spbau.farutin.homework01.commands;

import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.homework01.environment.Environment;
import ru.spbau.farutin.homework01.SessionStatus;
import ru.spbau.farutin.homework01.commands.arguments.Argument;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Executes one of system installed commands.
 */
public class ExternalCommand implements Command {
    private String name;
    private List<Argument> arguments;
    private Environment environment;

    public ExternalCommand(@NotNull String name,
                           @NotNull List<Argument> arguments,
                           @NotNull Environment environment) {
        this.name = name;
        this.arguments = arguments;
        this.environment = environment;
    }

    @Override
    public @NotNull CommandOutput execute() throws CommandException {
        List<String> command = new LinkedList<>();
        command.add(name);
        for (Argument argument : arguments) {
            command.add(argument.getValue());
        }

        ProcessBuilder builder = new ProcessBuilder(command);
        Map<String, String> processEnvironment = builder.environment();
        List<Environment.Entry> entryList = environment.entryList();
        for (Environment.Entry entry : entryList) {
            processEnvironment.put(entry.getKey(), entry.getValue());
        }

        Process process;
        try {
            process = builder.start();
        } catch (IOException e) {
            throw new SubprocessException(
                    String.format("Failed to start subprocess for command %s", name));
        }
        while (true) {
            try {
                process.waitFor();
                break;
            } catch (InterruptedException ignored) {
            }
        }

        InputStream inputStream = process.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter("\\A");

        String result = "";
        if (scanner.hasNext()) {
            result = scanner.next();
        }

        return new CommandOutput(result, SessionStatus.PROCEED);
    }
}
