package edu.put_the_machine.scrapper.model.parser;

import java.time.LocalTime;

public class LessonTimeInterval {
    private final LocalTime start;
    private final LocalTime end;

    public LessonTimeInterval(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }
}
