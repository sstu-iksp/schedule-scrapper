package edu.put_the_machine.scrapper.services.impl.parsers.sstu;

import edu.put_the_machine.scrapper.services.Parser;

public class SstuParser implements Parser {
    private final String pathToSchedulePage;
    private final String universityName;

    public SstuParser(String pathToSchedulePage, String universityName) {
        this.pathToSchedulePage = pathToSchedulePage;
        this.universityName = universityName;
    }
}
