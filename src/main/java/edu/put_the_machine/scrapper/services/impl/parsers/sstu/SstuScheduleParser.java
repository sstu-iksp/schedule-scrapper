package edu.put_the_machine.scrapper.services.impl.parsers.sstu;

import edu.put_the_machine.scrapper.services.ScheduleParser;
import edu.put_the_machine.scrapper.services.UrlToPageResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SstuScheduleParser implements ScheduleParser {
    private final UrlToPageResolver urlToPageResolver;
    private @Value("${parser.university.url.sstu}") String pathToSchedulePage;
    private @Value("${parser.university.name.sstu}") String universityName;

    public SstuScheduleParser(UrlToPageResolver urlToPageResolver) {
        this.urlToPageResolver = urlToPageResolver;
    }
}
