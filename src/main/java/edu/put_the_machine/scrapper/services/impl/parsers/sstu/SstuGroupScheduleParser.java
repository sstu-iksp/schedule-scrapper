package edu.put_the_machine.scrapper.services.impl.parsers.sstu;

import edu.put_the_machine.scrapper.exceptions.ParserException;
import edu.put_the_machine.scrapper.model.dto.*;
import edu.put_the_machine.scrapper.model.parser.LessonTimeInterval;
import edu.put_the_machine.scrapper.model.parser.RawLessonTimeInterval;
import edu.put_the_machine.scrapper.services.GroupScheduleParser;
import edu.put_the_machine.scrapper.services.UrlToPageResolver;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class SstuGroupScheduleParser implements GroupScheduleParser {
    private final UrlToPageResolver urlToPageResolver;
    private @Value("${parser.university.name.sstu}") String universityName;

    @Autowired
    public SstuGroupScheduleParser(UrlToPageResolver urlToPageResolver) {
        this.urlToPageResolver = urlToPageResolver;
    }

    @Override
    public List<ScheduleDayDto> parse(String path) {
        Document document = getDocumentFromPath(path);
        Elements scheduleCols = document.select("div.rasp-table-col");
        GroupDto group = createGroup(document);

        return getScheduleDaysFromColumns(scheduleCols, group);
    }

    private Document getDocumentFromPath(String path) {
        try {
            String htmlText = urlToPageResolver.getBodyAsString(path);
            return Jsoup.parse(htmlText);
        } catch (IOException e) {
            throw new ParserException("Resource '" + path + "' is unavailable.", e);
        } catch (IllegalArgumentException e) {
            throw new ParserException("String '" + path + "' is not a valid path.", e);
        }
    }

    private GroupDto createGroup(Document document) {
        //Format: Расписание GroupName
        String groupName = getTextByQueryOrThrowException(document, "div.h2").substring(11);
        return new GroupDto(groupName, universityName);
    }

    private List<ScheduleDayDto> getScheduleDaysFromColumns(Elements scheduleCols, GroupDto group) {
        List<ScheduleDayDto> scheduleDays = new ArrayList<>();

        for (Element col : scheduleCols) {
            LocalDate date = getDateByColumn(col);
            Elements lessonsCells = col.select("div.rasp-table-row");
            scheduleDays.add(createNewScheduleDay(lessonsCells, date, group));
        }
        return scheduleDays;
    }

    private LocalDate getDateByColumn(Element col) throws ParserException {
        String rawDate = getRawDate(col);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(rawDate, formatter);
    }

    /**
     *
     * @param scheduleColumn column in schedule which represents one day
     * @return Date string in format: dd.MM.yyyy
     */
    private String getRawDate(Element scheduleColumn) {
        int currentYear = getCurrentYear();
        Element dayInfo = scheduleColumn.select("div.rasp-table-row-header").first();
        String monthAndDay = getTextByQueryOrThrowException(dayInfo, "div.date");
        return monthAndDay + "." + currentYear;
    }

    private int getCurrentYear() {
        return LocalDateTime.now().getYear();
    }

    private ScheduleDayDto createNewScheduleDay(Elements lessonsCells, LocalDate date, GroupDto group) {
        List<LessonDto> lessons = createLessonsFromCells(lessonsCells);

        return new GroupScheduleDayDto(group, lessons, date, LocalDateTime.now());
    }

    private List<LessonDto> createLessonsFromCells(Elements lessonsCells) {
        List<LessonDto> result = new ArrayList<>();

        lessonsCells
                .stream()
                .filter((el) -> !el.hasClass("empty"))
                .forEach((el) -> result.add(createLesson(el)));

        return result;
    }

    private LessonDto createLesson(Element lessonElement) {
        String subject = getTextByQueryOrThrowException(lessonElement, "div.subject");
        String type = getTextByQueryOrThrowException(lessonElement, "div.type");
        LessonTimeInterval lessonTimeInterval = getLessonTimeInterval(lessonElement);
        TeacherDto teacher = createTeacherIfExists(lessonElement);
        String location = getTextByQueryOrThrowException(lessonElement, "div.aud");

        return new LessonDto(subject, type, lessonTimeInterval.getStart(), lessonTimeInterval.getEnd(), teacher, location);
    }

    private LessonTimeInterval getLessonTimeInterval(Element lessonElement) {
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
        String[] lessonRawTime = getTextByQueryOrThrowException(lessonElement, "div.rasp-table-inner-cell-hidden").split(" ");

        assert lessonRawTime.length == 4;

        return new RawLessonTimeInterval(lessonRawTime[1], lessonRawTime[3]);
    }

    private LocalTime parseLessonTime(String lessonTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:m");
        return LocalTime.parse(lessonTime, timeFormatter);
    }

    private TeacherDto createTeacherIfExists(Element lessonElement) {
        // There are two a tags inside the 'teacher' block. Only the second provides us with useful information.
        Element teacherElement = lessonElement.select("div.teacher > a").last();

        if (teacherElement == null)
            return null;

        String name = teacherElement.text();
        String url = teacherElement.attr("href");
        return new TeacherDto(name, url);
    }

    private String getTextByQueryOrThrowException(Element cell, String query) {
        Element element = cell.select(query).first();

        if (element != null)
            return element.text();

        throw new ParserException("Wrong HTML. There is not tag by query " + query);
    }
}
