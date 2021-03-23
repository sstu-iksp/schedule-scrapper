package edu.put_the_machine.scrapper.services.parsers;

import edu.put_the_machine.scrapper.model.dto.parser.GroupLessons;
import edu.put_the_machine.scrapper.service.impl.parsers.JsoupHelperImpl;
import edu.put_the_machine.scrapper.service.impl.parsers.sgu.SguGroupScheduleParser;
import edu.put_the_machine.scrapper.service.interfaces.UrlToPageResolver;
import edu.put_the_machine.scrapper.services.ParserServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SguGroupScheduleParserTest extends ParserServiceTest {
    private @Mock
    UrlToPageResolver urlToPageResolver;
    private @InjectMocks
    JsoupHelperImpl jsoupHelper;
    private SguGroupScheduleParser parser;
    private final static LocalDate FIXED_DATE = LocalDate.of(2021, 3, 23);

    @BeforeEach
    public void init() {
        parser = new SguGroupScheduleParser(jsoupHelper, FIXED_DATE);
    }

    @Test
    public void parseGroup0() throws IOException {
        String jsonResultPath = "src/test/recourses/parsers_tests_res/sgu/group0/sguGroup0ScheduleJson.json";
        String rawDataPath = "src/test/recourses/parsers_tests_res/sgu/group0/sguGroup0Schedule.html";
        parseTest(jsonResultPath, rawDataPath);
    }

    @Test
    public void parseGroup1() throws IOException {
        String jsonResultPath = "src/test/recourses/parsers_tests_res/sgu/group1/sguGroup1ScheduleJson.json";
        String rawDataPath = "src/test/recourses/parsers_tests_res/sgu/group1/sguGroup1Schedule.html";
        parseTest(jsonResultPath, rawDataPath);
    }

    @Test
    public void parseGroup2() throws IOException {
        String jsonResultPath = "src/test/recourses/parsers_tests_res/sgu/group2/sguGroup2ScheduleJson.json";
        String rawDataPath = "src/test/recourses/parsers_tests_res/sgu/group2/sguGroup2Schedule.html";
        parseTest(jsonResultPath, rawDataPath);
    }

    private void parseTest(String jsonResultPath, String rawDataPath) throws IOException {

        when(urlToPageResolver.getBodyAsString(rawDataPath)).thenReturn(Files.readString(Path.of(rawDataPath)));
        GroupLessons returnedLessons = parser.parse(rawDataPath);
        GroupLessons expectedLessons = getExpectedScheduleDaysFromJsonFile(jsonResultPath);

        assertEquals(expectedLessons, returnedLessons);
    }
}
