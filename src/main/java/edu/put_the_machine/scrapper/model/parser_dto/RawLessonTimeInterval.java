package edu.put_the_machine.scrapper.model.parser_dto;

public class RawLessonTimeInterval {
    private final String start;
    private final String end;

    public RawLessonTimeInterval(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
