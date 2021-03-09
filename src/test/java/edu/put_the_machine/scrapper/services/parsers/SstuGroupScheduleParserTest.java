package edu.put_the_machine.scrapper.services.parsers;

import edu.put_the_machine.scrapper.services.ParserServiceTest;
import edu.put_the_machine.scrapper.services.impl.parsers.JsoupHelperImpl;
import edu.put_the_machine.scrapper.services.impl.parsers.sstu.SstuGroupScheduleParser;
import edu.put_the_machine.scrapper.services.interfaces.UrlToPageResolver;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class SstuGroupScheduleParserTest extends ParserServiceTest {
    private @Mock UrlToPageResolver urlToPageResolver;
    private @InjectMocks JsoupHelperImpl jsoupHelper;
    private SstuGroupScheduleParser parser;
/*
    @BeforeEach
    public void init() {
        SstuDateTimeParser dateTimeParser = new SstuDateTimeParser(jsoupHelper);
        parser = new SstuGroupScheduleParser(jsoupHelper, dateTimeParser, "SSTU");
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

        List<ScheduleDayDto> returnedScheduleDays = parser.parse(rawDataPath);
        List<ScheduleDayDto> expectedScheduleDays = getExpectedScheduleDaysFromJsonFile(jsonResultPath);

        assertEquals(expectedScheduleDays, returnedScheduleDays);
    }*/
}
