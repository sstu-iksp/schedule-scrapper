package edu.put_the_machine.scrapper.services.parsers;

import edu.put_the_machine.scrapper.model.dto.parser.GroupLessons;
import edu.put_the_machine.scrapper.service.impl.parsers.sstu.SstuDateTimeParser;
import edu.put_the_machine.scrapper.services.ParserServiceTest;
import edu.put_the_machine.scrapper.service.impl.parsers.JsoupHelperImpl;
import edu.put_the_machine.scrapper.service.impl.parsers.sstu.SstuGroupScheduleParser;
import edu.put_the_machine.scrapper.service.interfaces.UrlToPageResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SstuGroupScheduleParserTest extends ParserServiceTest {
    private @Mock UrlToPageResolver urlToPageResolver;
    private @InjectMocks JsoupHelperImpl jsoupHelper;
    private SstuGroupScheduleParser parser;

    @BeforeEach
    public void init() {
        SstuDateTimeParser dateTimeParser = new SstuDateTimeParser(jsoupHelper);
        parser = new SstuGroupScheduleParser(jsoupHelper, dateTimeParser);
    }

    @Test
    public void parseGroup0() throws IOException {
        String jsonResultPath = "src/test/recourses/parsers_tests_res/sstu/group0/sstuGroup0ScheduleJson.json";
        String rawDataPath = "src/test/recourses/parsers_tests_res/sstu/group0/sstuGroup0Schedule.html";
        parseTest(jsonResultPath, rawDataPath);
    }

    @Test
    public void parseGroup1() throws IOException {
        String jsonResultPath = "src/test/recourses/parsers_tests_res/sstu/group1/sstuGroup1ScheduleJson.json";
        String rawDataPath = "src/test/recourses/parsers_tests_res/sstu/group1/sstuGroup1Schedule.html";
        parseTest(jsonResultPath, rawDataPath);
    }

    @Test
    public void parseGroup2() throws IOException {
        String jsonResultPath = "src/test/recourses/parsers_tests_res/sstu/group2/sstuGroup2ScheduleJson.json";
        String rawDataPath = "src/test/recourses/parsers_tests_res/sstu/group2/sstuGroup2Schedule.html";
        parseTest(jsonResultPath, rawDataPath);
    }

    @Test
    public void parseGroup3WithSubgroups() throws IOException {
        String jsonResultPath = "src/test/recourses/parsers_tests_res/sstu/group3WithSubgroups/sstuGroup3ScheduleJson.json";
        String rawDataPath = "src/test/recourses/parsers_tests_res/sstu/group3WithSubgroups/sstuGroup3Schedule.html";
        parseTest(jsonResultPath, rawDataPath);
    }

    @Test
    public void parseGroup4WithEmptyCells() throws IOException {
        String jsonResultPath = "src/test/recourses/parsers_tests_res/sstu/group4WithEmptyCells/sstuGroup4ScheduleJson.json";
        String rawDataPath = "src/test/recourses/parsers_tests_res/sstu/group4WithEmptyCells/sstuGroup4Schedule.html";
        parseTest(jsonResultPath, rawDataPath);
    }

    private void parseTest(String jsonResultPath, String rawDataPath) throws IOException {
        when(urlToPageResolver.getBodyAsString(rawDataPath)).thenReturn(Files.readString(Path.of(rawDataPath)));

        GroupLessons returnedLessons = parser.parse(rawDataPath);
        GroupLessons expectedLessons = getExpectedScheduleDaysFromJsonFile(jsonResultPath);

        assertEquals(expectedLessons, returnedLessons);
    }
}
