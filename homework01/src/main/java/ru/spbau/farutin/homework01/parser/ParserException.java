package ru.spbau.farutin.homework01.parser;

/**
 * ParserException is thrown if parser fails to parse input.
 */
public class ParserException extends Exception {
    public ParserException(String message) {
        super(message);
    }
}
