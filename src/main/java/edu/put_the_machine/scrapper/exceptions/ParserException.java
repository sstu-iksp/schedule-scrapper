package edu.put_the_machine.scrapper.exceptions;

public class ParserException extends RuntimeException {
    public ParserException(String message, Exception e) {
        super(message, e);
    }

    public ParserException(String message) {
        super(message);
    }
}
