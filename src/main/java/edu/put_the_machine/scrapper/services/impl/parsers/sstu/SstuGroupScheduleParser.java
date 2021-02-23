package edu.put_the_machine.scrapper.services.impl.parsers.sstu;

import edu.put_the_machine.scrapper.model.dto.LessonDto;
import edu.put_the_machine.scrapper.model.dto.ScheduleDayDto;
import edu.put_the_machine.scrapper.exceptions.ParserException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import edu.put_the_machine.scrapper.services.UrlToPageResolver;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SstuGroupScheduleParser {
    private final UrlToPageResolver urlToPageResolver;
    private final String universityName;

    public SstuGroupScheduleParser(UrlToPageResolver urlToPageResolver, String universityName) {
        this.urlToPageResolver = urlToPageResolver;
        this.universityName = universityName;
    }

    public List<ScheduleDayDto> parse(String path) {
        Document document = getDocumentFromPath(path);
        Elements scheduleCols = document.select("div.rasp-table-col");

        return getScheduleDaysFromColumns(scheduleCols);
    }

    private Document getDocumentFromPath(String path) {
        try {
            String htmlText = urlToPageResolver.getHtmlTextFromUrl(path);
            return Jsoup.parse(htmlText);
        } catch (IOException e) {
            throw new ParserException("Resource '" + path + "' is unavailable.", e);
        } catch (IllegalArgumentException e) {
            throw new ParserException("String '" + path + "' is not a valid path.", e);
        }
    }

    private List<ScheduleDayDto> getScheduleDaysFromColumns(Elements scheduleCols) {
        List<ScheduleDayDto> scheduleDays = new ArrayList<>();
        for (Element col : scheduleCols) {
            LocalDate date = getDateByColumn(col);
            Elements lessonsCells = col.select("div.rasp-table-row");
            scheduleDays.add(createNewScheduleDay(lessonsCells, date));
        }
        return scheduleDays;
    }

    private ScheduleDayDto createNewScheduleDay(Elements lessonsCells, LocalDate date) {
        List<LessonDto> lesson = createLessonsFromCells(lessonsCells);
        return null;
    }

    private List<LessonDto> createLessonsFromCells(Elements lessonsCells) {
        List<LessonDto> result = new ArrayList<>();
        return null;
    }

    private String getTextByQueryOrEmpty(Element cell, String query) {
        Element element = cell.select(query).first();
        return (element == null) ? "" : element.text();
    }

    private LocalDate getDateByColumn(Element col) throws ParserException {
        String rawDate = getRawDate(col);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(rawDate, formatter);
    }

    private String getRawDate(Element col) {
        int currentYear = getCurrentYear();
        Element dayInfo = col.select("div.rasp-table-row-header").first();
        String monthAndDay = dayInfo.select("div.date").text();
        return monthAndDay + "." + currentYear;
    }

    private int getCurrentYear() {
        return LocalDateTime.now().getYear();
    }
}
