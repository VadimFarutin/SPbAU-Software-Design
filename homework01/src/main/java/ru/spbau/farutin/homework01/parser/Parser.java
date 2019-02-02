package ru.spbau.farutin.homework01.parser;

import ru.spbau.farutin.homework01.commands.Command;

/**
 * CLI parser.
 */
public interface Parser {
    /**
     * Parses given line and builds corresponding command.
     * @param line line to parse
     * @return command representing given line
     * @throws ParserException if failed to parse the line
     */
    Command parse(String line) throws ParserException;
}
