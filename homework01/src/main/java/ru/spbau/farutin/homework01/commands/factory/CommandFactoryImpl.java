package ru.spbau.farutin.homework01.commands.factory;

import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.homework01.environment.Environment;
import ru.spbau.farutin.homework01.commands.*;
import ru.spbau.farutin.homework01.commands.arguments.Argument;
import ru.spbau.farutin.homework01.commands.arguments.CommandArgument;

import java.util.List;
import java.util.Optional;

public class CommandFactoryImpl implements CommandFactory {
    private Environment environment;

    public CommandFactoryImpl(@NotNull Environment environment) {
        this.environment = environment;
    }

    @Override
    public @NotNull Command buildCommand(@NotNull String name,
                                         @NotNull List<Argument> arguments,
                                         @NotNull Optional<Command> prevCommand) {
        prevCommand.ifPresent(command -> arguments.add(new CommandArgument(command)));

        if (name.equals("=")) {
            return new AssignmentCommand(arguments, environment);
        } else if (name.equals("cat")) {
            return new CatCommand(arguments);
        } else if (name.equals("echo")) {
            return new EchoCommand(arguments);
        } else if (name.equals("wc")) {
            return new WcCommand(arguments);
        } else if (name.equals("pwd")) {
            return new PwdCommand(arguments);
        } else if (name.equals("exit")) {
            return new ExitCommand(arguments);
        } else if (name.equals("grep")) {
            return new GrepCommand(arguments);
        }

        return new ExternalCommand(name, arguments, environment);
    }
}
