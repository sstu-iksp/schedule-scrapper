package edu.put_the_machine.scrapper.services.impl.parsers.sstu;

import edu.put_the_machine.scrapper.model.dto.ScheduleDayDto;
import edu.put_the_machine.scrapper.services.GroupScheduleParser;
import edu.put_the_machine.scrapper.services.ScheduleParser;
import edu.put_the_machine.scrapper.services.UrlToPageResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SstuScheduleParser implements ScheduleParser {
    private final UrlToPageResolver urlToPageResolver;
    private final GroupScheduleParser sstuGroupScheduleParser;
    private @Value("${parser.university.url.sstu}") String pathToSchedulePage;
    private @Value("${parser.university.name.sstu}") String universityName;

    @Autowired
    public SstuScheduleParser(UrlToPageResolver urlToPageResolver, SstuGroupScheduleParser sstuGroupScheduleParser) {
        this.urlToPageResolver = urlToPageResolver;
        this.sstuGroupScheduleParser = sstuGroupScheduleParser;
    }

    @Override
    public List<ScheduleDayDto> parse() {
        return null;
    }
}
