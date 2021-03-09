package edu.put_the_machine.scrapper.service.impl.parsers.sstu;

import edu.put_the_machine.scrapper.exceptions.ParserException;
import edu.put_the_machine.scrapper.model.dto.*;
import edu.put_the_machine.scrapper.model.parser_dto.GroupLessons;
import edu.put_the_machine.scrapper.model.parser_dto.LessonParserDto;
import edu.put_the_machine.scrapper.model.parser_dto.LessonTimeInterval;
import edu.put_the_machine.scrapper.model.parser_dto.TeacherParserDto;
import edu.put_the_machine.scrapper.service.interfaces.parser.DateTimeParser;
import edu.put_the_machine.scrapper.service.interfaces.parser.GroupScheduleParser;
import edu.put_the_machine.scrapper.service.interfaces.parser.JsoupHelper;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SstuGroupScheduleParser implements GroupScheduleParser {
    private final JsoupHelper jsoupHelper;
    private final DateTimeParser sstuDateTimeParser;

    @Autowired
    public SstuGroupScheduleParser(JsoupHelper jsoupHelper, DateTimeParser sstuDateTimeParser) {
        this.jsoupHelper = jsoupHelper;
        this.sstuDateTimeParser = sstuDateTimeParser;
    }

    @Override
    public GroupLessons parse(String path) {
        Document document = jsoupHelper.getDocumentFromPath(path);
        Elements scheduleCols = document.select("div.rasp-table-col");
        String groupName = getGroupName(document);
        List<LessonParserDto> lessons = getLessonsFromColumns(scheduleCols);
        LocalDate start = getLessonsStartDate(lessons);
        LocalDate end = getLessonsEndDate(lessons);

        return new GroupLessons(groupName, lessons, start, end);
    }

    @NotNull
    private String getGroupName(Document document) {
        //Format: Расписание GroupName
        return getTextByQueryOrThrowException(document, "div.h2").substring(11);
    }

    private List<LessonParserDto> getLessonsFromColumns(Elements scheduleCols) {
        List<LessonParserDto> lessons = new ArrayList<>();

        for (Element col : scheduleCols) {
            LocalDate date = sstuDateTimeParser.getDateByColumn(col);
            Elements lessonsCells = col.select("div.rasp-table-row");
            lessons.addAll(createLessons(lessonsCells, date));
        }
        return lessons;
    }

    private List<LessonParserDto> createLessons(Elements lessonsCells, LocalDate date) {
        List<LessonParserDto> result = new ArrayList<>();

        lessonsCells
                .stream()
                .filter(this::isNotEmpty)
                .forEach((el) -> {
                    if (subgroupedLesson(el)) {
                        result.addAll(createSubgroupLessons(el, date));
                    } else {
                        result.add(createLesson(el, date));
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

    private List<LessonParserDto> createSubgroupLessons(Element lessonElement, LocalDate date) {
        List<LessonParserDto> result = new ArrayList<>();

        String subject = parseSubgroupLessonSubject(lessonElement);
        String type = getTextByQueryOrThrowException(lessonElement, "span.type");
        LessonTimeInterval lessonTimeInterval = sstuDateTimeParser.getLessonTimeInterval(lessonElement, date);

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

    private LessonParserDto createSubLesson(Element subLessonElement, String subject, String type, LessonTimeInterval lessonTimeInterval) {
        TeacherParserDto teacher = createTeacherIfExists(subLessonElement);
        String location = getTextByQueryOrThrowException(subLessonElement, "span.aud");
        return new LessonParserDto(subject, type, location, teacher, lessonTimeInterval);
    }

    private LessonParserDto createLesson(Element lessonElement, LocalDate date) {
        String subject = getTextByQueryOrThrowException(lessonElement, "div.subject");
        String type = getTextByQueryOrThrowException(lessonElement, "div.type");
        LessonTimeInterval lessonTimeInterval = sstuDateTimeParser.getLessonTimeInterval(lessonElement, date);
        TeacherParserDto teacher = createTeacherIfExists(lessonElement);
        String location = getTextByQueryOrThrowException(lessonElement, "div.aud");

        return new LessonParserDto(subject, type, location, teacher, lessonTimeInterval);
    }

    private TeacherParserDto createTeacherIfExists(Element lessonElement) {
        Element teacherElement = lessonElement.select("div.teacher").last();

        if (teacherElement == null || teacherElement.text().isBlank())
            return null;

        String name = teacherElement.text();

        // There are two 'a' tags inside the 'teacher' block. Only the second provides us with useful information.
        Element teacherATag = teacherElement.select("a").last();

        String url = (teacherATag == null) ? null : teacherATag.attr("href");

        return new TeacherParserDto(name, url);
    }

    private String getTextByQueryOrThrowException(Element cell, String query) {
        Element element = cell.select(query).first();

        if (element != null)
            return element.text();

        throw new ParserException("Wrong HTML. There is no tag by query " + query);
    }

    private LocalDate getLessonsStartDate(List<LessonParserDto> lessons) {
        return lessons
                .stream()
                .min(Comparator.comparing(les -> les.getLessonTimeInterval().getWhen()))
                .map((les) -> les.getLessonTimeInterval().getWhen())
                .orElse(null);
    }

    private LocalDate getLessonsEndDate(List<LessonParserDto> lessons) {
        return lessons
                .stream()
                .max(Comparator.comparing(les -> les.getLessonTimeInterval().getWhen()))
                .map((les) -> les.getLessonTimeInterval().getWhen())
                .orElse(null);
    }
}
