package edu.put_the_machine.scrapper.services.impl.parsers.sstu;

import edu.put_the_machine.scrapper.exceptions.ParserException;
import edu.put_the_machine.scrapper.model.dto.*;
import edu.put_the_machine.scrapper.model.parser.LessonTimeInterval;
import edu.put_the_machine.scrapper.services.interfaces.parser.DateTimeParser;
import edu.put_the_machine.scrapper.services.interfaces.parser.GroupScheduleParser;
import edu.put_the_machine.scrapper.services.interfaces.parser.JsoupHelper;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SstuGroupScheduleParser implements GroupScheduleParser {
    private final JsoupHelper jsoupHelper;
    private final DateTimeParser sstuDateTimeParser;
    private final String universityName;

    @Autowired
    public SstuGroupScheduleParser(JsoupHelper jsoupHelper, DateTimeParser sstuDateTimeParser,
                                   @Value("${parser.university.name.sstu}") String universityName) {
        this.jsoupHelper = jsoupHelper;
        this.sstuDateTimeParser = sstuDateTimeParser;
        this.universityName = universityName;
    }

    @Override
    public List<ScheduleDayDto> parse(String path) {
        Document document = jsoupHelper.getDocumentFromPath(path);
        Elements scheduleCols = document.select("div.rasp-table-col");
        GroupDto group = createGroup(document);

        return getScheduleDaysFromColumns(scheduleCols, group);
    }

    private GroupDto createGroup(Document document) {
        //Format: Расписание GroupName
        String groupName = getTextByQueryOrThrowException(document, "div.h2").substring(11);
        return new GroupDto(groupName, universityName);
    }

    private List<ScheduleDayDto> getScheduleDaysFromColumns(Elements scheduleCols, GroupDto group) {
        List<ScheduleDayDto> scheduleDays = new ArrayList<>();

        for (Element col : scheduleCols) {
            LocalDate date = sstuDateTimeParser.getDateByColumn(col);
            Elements lessonsCells = col.select("div.rasp-table-row");
            scheduleDays.add(createNewScheduleDay(lessonsCells, date, group));
        }
        return scheduleDays;
    }

    private ScheduleDayDto createNewScheduleDay(Elements lessonsCells, LocalDate date, GroupDto group) {
        List<LessonDto> lessons = createLessonsFromCells(lessonsCells);

        return new GroupScheduleDayDto(group, lessons, date, LocalDateTime.now());
    }

    private List<LessonDto> createLessonsFromCells(Elements lessonsCells) {
        List<LessonDto> result = new ArrayList<>();

        lessonsCells
                .stream()
                .filter(this::isNotEmpty)
                .forEach((el) -> {
                    if (subgroupedLesson(el)) {
                        result.addAll(createSubgroupLessons(el));
                    } else {
                        result.add(createLesson(el));
                    }
                });

        return result;
    }

    private boolean isNotEmpty(Element cell) {
        return !cell.hasClass("empty") &&
               !cell.select("div.rasp-table-inner-cell")
                       .last()
                       .html()
                       //There is a '&nbsp;' symbol which has to be removed for clean check.
                       .replaceAll("&nbsp;", "")
                       .isBlank();
    }

    private boolean subgroupedLesson(Element lessonElement) {
        return !lessonElement.select("div.subgroup-info").isEmpty();
    }

    private List<LessonDto> createSubgroupLessons(Element lessonElement) {
        List<LessonDto> result = new ArrayList<>();

        String subject = parseSubgroupLessonSubject(lessonElement);
        String type = getTextByQueryOrThrowException(lessonElement, "span.type");
        LessonTimeInterval lessonTimeInterval = sstuDateTimeParser.getLessonTimeInterval(lessonElement);

        for (Element subLessonElement : lessonElement.select("div.subgroup-info")) {
            result.add(createSubLesson(subLessonElement, subject, type, lessonTimeInterval));
        }

        return result;
    }

    @NotNull
    private String parseSubgroupLessonSubject(Element lessonElement) {
        String subject = getTextByQueryOrThrowException(lessonElement, "div.subject-m");
        // subject's div ends with info about type which starts with '(' character
        int lastSubjectNameIndex = subject.lastIndexOf('(') - 1;
        subject = subject.substring(0, lastSubjectNameIndex);
        return subject;
    }

    private LessonDto createSubLesson(Element subLessonElement, String subject, String type, LessonTimeInterval lessonTimeInterval) {
        TeacherDto teacher = createTeacherIfExists(subLessonElement);
        String location = getTextByQueryOrThrowException(subLessonElement, "span.aud");
        return new LessonDto(subject, type, lessonTimeInterval.getStart(), lessonTimeInterval.getEnd(), teacher, location);
    }

    private LessonDto createLesson(Element lessonElement) {
        String subject = getTextByQueryOrThrowException(lessonElement, "div.subject");
        String type = getTextByQueryOrThrowException(lessonElement, "div.type");
        LessonTimeInterval lessonTimeInterval = sstuDateTimeParser.getLessonTimeInterval(lessonElement);
        TeacherDto teacher = createTeacherIfExists(lessonElement);
        String location = getTextByQueryOrThrowException(lessonElement, "div.aud");

        return new LessonDto(subject, type, lessonTimeInterval.getStart(), lessonTimeInterval.getEnd(), teacher, location);
    }

    private TeacherDto createTeacherIfExists(Element lessonElement) {
        Element teacherElement = lessonElement.select("div.teacher").last();

        if (teacherElement == null || teacherElement.text().isBlank())
            return null;

        String name = teacherElement.text();

        // There are two 'a' tags inside the 'teacher' block. Only the second provides us with useful information.
        Element teacherATag = teacherElement.select("a").last();

        String url = (teacherATag == null) ? null : teacherATag.attr("href");
        return new TeacherDto(name, url);
    }

    private String getTextByQueryOrThrowException(Element cell, String query) {
        Element element = cell.select(query).first();

        if (element != null)
            return element.text();

        throw new ParserException("Wrong HTML. There is no tag by query " + query);
    }
}
