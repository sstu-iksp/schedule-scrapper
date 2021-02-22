package edu.put_the_machine.scrapper.exceptions;

public class ParserException extends RuntimeException {
    public ParserException(String s, Exception e) {
        super(s, e);
    }
}
