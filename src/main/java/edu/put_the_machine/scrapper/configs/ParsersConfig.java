package edu.put_the_machine.scrapper.configs;

import edu.put_the_machine.scrapper.model.University;
import edu.put_the_machine.scrapper.repo.UniversityRepo;
import edu.put_the_machine.scrapper.services.Parser;
import edu.put_the_machine.scrapper.services.UrlToPageResolver;
import edu.put_the_machine.scrapper.services.impl.parsers.sstu.SstuParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParsersConfig {
    private final UniversityRepo universityRepo;
    private final UrlToPageResolver urlToPageResolver;

    @Autowired
    public ParsersConfig(UniversityRepo universityRepo, UrlToPageResolver urlToPageResolver) {
        this.universityRepo = universityRepo;
        this.urlToPageResolver = urlToPageResolver;
    }

    @Bean
    public Parser sstuParser(@Value("params.parsers.urls.sstu") String url) {
        University university = universityRepo.findByParserName(SstuParser.class.getName());
        return new SstuParser(url, university.getName());
    }
}
