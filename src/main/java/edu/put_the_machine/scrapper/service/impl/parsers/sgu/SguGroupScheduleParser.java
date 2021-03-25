package edu.put_the_machine.scrapper.service.impl.parsers.sgu;

import edu.put_the_machine.scrapper.model.dto.parser.GroupLessons;
import edu.put_the_machine.scrapper.model.dto.parser.LessonParserDto;
import edu.put_the_machine.scrapper.model.dto.parser.LessonTimeInterval;
import edu.put_the_machine.scrapper.model.dto.parser.TeacherParserDto;
import edu.put_the_machine.scrapper.service.interfaces.parser.GroupScheduleParser;
import edu.put_the_machine.scrapper.service.interfaces.parser.JsoupHelper;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class SguGroupScheduleParser implements GroupScheduleParser {
    private final JsoupHelper jsoupHelper;
    private LocalDate currentDate;

    @Autowired
    public SguGroupScheduleParser(JsoupHelper jsoupHelper) {
        this.jsoupHelper = jsoupHelper;
    }

    @Override
    public GroupLessons parse(String path) {
        if (currentDate == null) {
            currentDate = LocalDate.now();
        }
        Document document = jsoupHelper.getDocumentFromPath(path);
        Elements scheduleRows = document.select("table#schedule > tbody > tr");
        String groupName = getGroupName(document);
        List<LessonParserDto> lessons = getLessonsFromRows(scheduleRows);
        currentDate = null;
        return new GroupLessons(groupName, lessons);
    }

    private List<LessonParserDto> getLessonsFromRows(Elements scheduleRows) {
        val lessons = new ArrayList<LessonParserDto>();
        val firstDate = currentDate.with(DayOfWeek.MONDAY);
        boolean isEven = firstDate.get(WeekFields.of(Locale.ENGLISH).weekOfYear()) % 2 == 0;
        LocalDate evenWeekDate, oddWeekDate;
        if (isEven) {
            evenWeekDate = firstDate.plusDays(7);
            oddWeekDate = firstDate;
        } else {
            evenWeekDate = firstDate;
            oddWeekDate = firstDate.plusDays(7);
        }
        for (int i = 1; i < scheduleRows.size(); i++) {
            lessons.addAll(createLessons(scheduleRows.get(i), evenWeekDate, oddWeekDate));
        }
        return lessons;
    }

    private List<LessonParserDto> createLessons(Element scheduleRow, LocalDate evenWeekDate, LocalDate oddWeekDate) {
        val lessons = new ArrayList<LessonParserDto>();
        String[] time = jsoupHelper.getTextByQueryOrThrowException(scheduleRow, "th").split(" ");
        val start = LocalTime.parse(time[0]);
        val end = LocalTime.parse(time[1]);
        Elements lessonsCells = scheduleRow.select("td");
        for (Element lessonCell : lessonsCells) {
            val evenInterval = new LessonTimeInterval(start, end, evenWeekDate);
            val oddInterval = new LessonTimeInterval(start, end, oddWeekDate);
            Elements lessonElements = lessonCell.select("div.l");
            for (Element lessonElement : lessonElements) {
                lessons.addAll(createLessonForTwoWeeks(lessonElement, evenInterval, oddInterval));
            }
            evenWeekDate = evenWeekDate.plusDays(1);
            oddWeekDate = oddWeekDate.plusDays(1);
        }
        return lessons;
    }

    private List<LessonParserDto> createLessonForTwoWeeks(Element lessonElement, LessonTimeInterval evenInterval, LessonTimeInterval oddInterval) {
        val lessons = new ArrayList<LessonParserDto>();
        String subject = jsoupHelper.getTextByQueryOrThrowException(lessonElement, "div.l-dn");
        String type = jsoupHelper.getTextByQueryOrThrowException(lessonElement, "div.l-pr-t");
        String week = jsoupHelper.getTextByQueryOrThrowException(lessonElement, "div.l-pr-r");
        String location = jsoupHelper.getTextByQueryOrThrowException(lessonElement, "div.l-p");
        String teacherName, teacherURL = null;
        Element teacherEl = lessonElement.select("div.l-tn > a").first();
        if (teacherEl == null) {
            teacherName = jsoupHelper.getTextByQueryOrThrowException(lessonElement, "div.l-tn");
        } else {
            teacherName = teacherEl.text();
            teacherURL = teacherEl.attr("href");
        }

        val teacher = new TeacherParserDto(teacherName, teacherURL);
        if (!week.equals("чис.")) {
            lessons.add(new LessonParserDto(subject, type, location, teacher, evenInterval));
        }
        if (!week.equals("знам.")) {
            lessons.add(new LessonParserDto(subject, type, location, teacher, oddInterval));
        }
        return lessons;
    }

    @NotNull
    private String getGroupName(Document document) {
        //Format: ДНЕВНОЕ ОТДЕЛЕНИЕ: GroupName Группа
        String instituteName = document.select("div.breadcrumbs a").last().text();
        return instituteName + " " + document.select("h1.page-title").first().text().split(" ")[2];
    }
}