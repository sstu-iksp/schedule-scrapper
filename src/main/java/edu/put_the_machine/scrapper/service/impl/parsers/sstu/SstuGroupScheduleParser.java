package edu.put_the_machine.scrapper.service.impl.parsers.sstu;

import edu.put_the_machine.scrapper.exceptions.ParserException;
import edu.put_the_machine.scrapper.model.dto.parser.GroupLessons;
import edu.put_the_machine.scrapper.model.dto.parser.LessonParserDto;
import edu.put_the_machine.scrapper.model.dto.parser.LessonTimeInterval;
import edu.put_the_machine.scrapper.model.dto.parser.TeacherParserDto;
import edu.put_the_machine.scrapper.service.interfaces.parser.DateTimeParser;
import edu.put_the_machine.scrapper.service.interfaces.parser.GroupScheduleParser;
import edu.put_the_machine.scrapper.service.interfaces.parser.JsoupHelper;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        return new GroupLessons(groupName, lessons);
    }

    @NotNull
    private String getGroupName(Document document) {
        //Format: Расписание GroupName
        val header = getTextByQueryOrThrowException(document, "div.h2");
        return header.substring(header.lastIndexOf(" ") + 1);
    }

    private List<LessonParserDto> getLessonsFromColumns(Elements scheduleCols) {
        return scheduleCols.stream()
                .flatMap(col -> {
                    LocalDate date = sstuDateTimeParser.getDateByColumn(col);
                    Elements lessonsCells = col.select("div.rasp-table-row");
                    return createLessons(lessonsCells, date).stream();
                })
                .collect(Collectors.toList());
    }

    private List<LessonParserDto> createLessons(Elements lessonsCells, LocalDate date) {
        return lessonsCells
                .stream()
                .filter(this::isNotEmpty)
                .flatMap(el -> {
                    if (subgroupedLesson(el)) {
                        return createSubgroupLessons(el, date).stream();
                    } else {
                        return Stream.of(createLesson(el, date));
                    }
                })
                .collect(Collectors.toList());
    }

    private boolean isNotEmpty(Element cell) {
        return !cell.hasClass("empty") &&
               !cell.select("div.rasp-table-inner-cell")
                       .last()
                       .html()
                       //There is a '&nbsp;' symbol which has to be removed for clean check.
                       .replace("&nbsp;", "")
                       .isBlank();
    }

    private boolean subgroupedLesson(Element lessonElement) {
        return !lessonElement.select("div.subgroup-info").isEmpty();
    }

    private List<LessonParserDto> createSubgroupLessons(Element lessonElement, LocalDate date) {

        String subject = parseSubgroupLessonSubject(lessonElement);
        String type = getTextByQueryOrThrowException(lessonElement, "span.type");
        LessonTimeInterval lessonTimeInterval = sstuDateTimeParser.getLessonTimeInterval(lessonElement, date);

        return lessonElement.select("div.subgroup-info").stream()
                .map(subLessonElement -> createSubLesson(subLessonElement, subject, type, lessonTimeInterval))
                .collect(Collectors.toList());
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

}
