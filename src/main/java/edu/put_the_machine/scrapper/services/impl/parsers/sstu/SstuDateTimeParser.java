package edu.put_the_machine.scrapper.services.impl.parsers.sstu;

import edu.put_the_machine.scrapper.exceptions.ParserException;
import edu.put_the_machine.scrapper.model.parser.LessonTimeInterval;
import edu.put_the_machine.scrapper.model.parser.RawLessonTimeInterval;
import edu.put_the_machine.scrapper.services.interfaces.parser.DateTimeParser;
import edu.put_the_machine.scrapper.services.interfaces.parser.JsoupHelper;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class SstuDateTimeParser implements DateTimeParser {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:m");
    private final JsoupHelper jsoupHelper;

    public SstuDateTimeParser(JsoupHelper jsoupHelper) {
        this.jsoupHelper = jsoupHelper;
    }


    /**
     *
     * @param scheduleColumn column of schedule table
     * @return LocalDate object which represents the schedule day
     * @throws ParserException if column element doesn't have a date div or it has wrong format.
     */
    @Override
    public LocalDate getDateByColumn(Element scheduleColumn) throws ParserException {
        String rawDate = getRawDate(scheduleColumn);
        return parseDate(rawDate);
    }

    private LocalDate parseDate(String rawDate) {
        try {
            return LocalDate.parse(rawDate, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new ParserException("Date in column has wrong format. It has to be dd.MM.yyyy but was " + rawDate, e);
        }
    }

    /**
     *
     * @param scheduleColumn column in schedule which represents one day
     * @return Date string in format: dd.MM.yyyy
     */
    private String getRawDate(Element scheduleColumn) {
        int currentYear = getCurrentYear();
        Element dayInfo = scheduleColumn.select("div.rasp-table-row-header").first();
        String monthAndDay = jsoupHelper.getTextByQueryOrThrowException(dayInfo, "div.date");
        return monthAndDay + "." + currentYear;
    }

    private int getCurrentYear() {
        return LocalDateTime.now().getYear();
    }

    @Override
    public LessonTimeInterval getLessonTimeInterval(Element lessonElement) throws ParserException {
        RawLessonTimeInterval rawLessonTimeInterval = getLessonRawTime(lessonElement);
        LocalTime start = parseLessonTime(rawLessonTimeInterval.getStart());
        LocalTime end = parseLessonTime(rawLessonTimeInterval.getEnd());
        return new LessonTimeInterval(start, end);
    }

    /**
     *
     * @param lessonElement lesson div block
     * @return RawLessonTimeInterval with start and end string values by format: hh:mm
     */
    private RawLessonTimeInterval getLessonRawTime(Element lessonElement) {
        // Text has next format: "lessonNumber hh:mm - hh:mm".
        // Example: "1 8:00 - 9:30"
        String[] lessonRawTime = jsoupHelper.getTextByQueryOrThrowException(lessonElement, "div.rasp-table-inner-cell-hidden").split(" ");

        assert lessonRawTime.length == 4;

        return new RawLessonTimeInterval(lessonRawTime[1], lessonRawTime[3]);
    }

    private LocalTime parseLessonTime(String lessonTime) {
        try {
            return LocalTime.parse(lessonTime, timeFormatter);
        } catch (DateTimeParseException e) {
            throw new ParserException("Lesson's time has wrong format. It has to be hh.mm but was " + lessonTime, e);
        }
    }
}
