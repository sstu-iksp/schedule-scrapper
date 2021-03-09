package edu.put_the_machine.scrapper.service.interfaces.parser;

import edu.put_the_machine.scrapper.exceptions.ParserException;
import edu.put_the_machine.scrapper.model.parser_dto.LessonTimeInterval;
import org.jsoup.nodes.Element;

import java.time.LocalDate;

public interface DateTimeParser {
    LocalDate getDateByColumn(Element scheduleColumn) throws ParserException;
    LessonTimeInterval getLessonTimeInterval(Element lessonElement, LocalDate date) throws ParserException;
}
