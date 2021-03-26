package edu.put_the_machine.scrapper.service.impl;

import edu.put_the_machine.scrapper.exceptions.ParserException;
import edu.put_the_machine.scrapper.model.dto.parser.UniversityLessons;
import edu.put_the_machine.scrapper.service.UniversityScheduleResolver;
import edu.put_the_machine.scrapper.service.interfaces.ScrapperService;
import edu.put_the_machine.scrapper.service.interfaces.parser.ScheduleParser;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ScrapperServiceImpl implements ScrapperService {
    private final List<ScheduleParser> scheduleParsers;
    private final UniversityScheduleResolver universityScheduleResolver;

    public ScrapperServiceImpl(List<ScheduleParser> scheduleParsers, UniversityScheduleResolver universityScheduleResolver) {
        this.scheduleParsers = scheduleParsers;
        this.universityScheduleResolver = universityScheduleResolver;
    }

    @Override
    public void scrap() {
        for (ScheduleParser parser : scheduleParsers) {
            tryParse(parser);
        }
    }

    private void tryParse(ScheduleParser parser) {
        try {
            dealWithSchedule(parser);
        } catch (ParserException e) {
            log.error("Error occurred while parsing via {} with message '{}'", parser, e.getMessage());
        }
    }

    private void dealWithSchedule(ScheduleParser parser) {
        UniversityLessons lessons = parser.parse();
        universityScheduleResolver.resolve(lessons);
    }
}
