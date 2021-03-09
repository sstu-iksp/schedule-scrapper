package edu.put_the_machine.scrapper.services.parsers;

import edu.put_the_machine.scrapper.services.ParserServiceTest;
import edu.put_the_machine.scrapper.services.impl.parsers.sstu.SstuScheduleParser;
import edu.put_the_machine.scrapper.services.interfaces.parser.GroupScheduleParser;
import edu.put_the_machine.scrapper.services.interfaces.parser.JsoupHelper;
import org.mockito.Mock;


public class SstuScheduleParserTest extends ParserServiceTest {
    private final String pathToSchedulePage = "src/test/recourses/parsers_tests_res/sstu/groupsPageHtml.html";
    private @Mock JsoupHelper jsoupHelper;
    private @Mock GroupScheduleParser sstuGroupScheduleParser;
    private SstuScheduleParser sstuScheduleParser;/*
    private List<ScheduleDayDto> group0ScheduleDays;
    private List<ScheduleDayDto> group1ScheduleDays;
    private List<ScheduleDayDto> group2ScheduleDays;
    private List<ScheduleDayDto> group3ScheduleDays;
    private List<ScheduleDayDto> group4ScheduleDays;


    @BeforeEach
    public void init() {
        String jsonResultPath0 = "src/test/recourses/parsers_tests_res/sstu/group0/sstuGroup0ScheduleJson.json";
        String jsonResultPath1 = "src/test/recourses/parsers_tests_res/sstu/group1/sstuGroup1ScheduleJson.json";
        String jsonResultPath2 = "src/test/recourses/parsers_tests_res/sstu/group2/sstuGroup2ScheduleJson.json";
        String jsonResultPath3 = "src/test/recourses/parsers_tests_res/sstu/group3WithSubgroups/sstuGroup3ScheduleJson.json";
        String jsonResultPath4 = "src/test/recourses/parsers_tests_res/sstu/group4WithEmptyCells/sstuGroup4ScheduleJson.json";
        group0ScheduleDays = getExpectedScheduleDaysFromJsonFile(jsonResultPath0);
        group1ScheduleDays = getExpectedScheduleDaysFromJsonFile(jsonResultPath1);
        group2ScheduleDays = getExpectedScheduleDaysFromJsonFile(jsonResultPath2);
        group3ScheduleDays = getExpectedScheduleDaysFromJsonFile(jsonResultPath3);
        group4ScheduleDays = getExpectedScheduleDaysFromJsonFile(jsonResultPath4);

        sstuScheduleParser = new SstuScheduleParser(jsoupHelper, sstuGroupScheduleParser, pathToSchedulePage);
    }

    @Test
    void parse() throws IOException {
        prepareMocks();

        List<ScheduleDayDto> returnedScheduleDays = sstuScheduleParser.parse();
        List<ScheduleDayDto> expectedScheduleDays = getExpectedScheduleDays();

        assertAll(
                () -> assertEquals(expectedScheduleDays.size(), returnedScheduleDays.size()),
                () -> assertTrue(expectedScheduleDays.containsAll(returnedScheduleDays)),
                () -> assertTrue(returnedScheduleDays.containsAll(expectedScheduleDays)),
                () -> assertFalse(returnedScheduleDays.contains(null))
        );
        //Parser has to find only group hrefs.
        verify(sstuGroupScheduleParser, never()).parse(contains("static"));
    }

    @NotNull
    private List<ScheduleDayDto> getExpectedScheduleDays() {
        return Stream.of(group0ScheduleDays, group1ScheduleDays, group2ScheduleDays, group3ScheduleDays, group4ScheduleDays)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
    }

    private void prepareMocks() throws IOException {
        String group0Path = pathToSchedulePage + "/group0";
        String group1Path = pathToSchedulePage + "/group1";
        String group2Path = pathToSchedulePage + "/group2";
        String group3Path = pathToSchedulePage + "/group3WithSubgroups";
        String group4Path = pathToSchedulePage + "/group4WithEmptyCells";

        when(jsoupHelper.getDocumentFromPath(pathToSchedulePage)).thenReturn(getHtmlDocument(pathToSchedulePage));
        when(sstuGroupScheduleParser.parse(group0Path)).thenReturn(group0ScheduleDays);
        when(sstuGroupScheduleParser.parse(group1Path)).thenReturn(group1ScheduleDays);
        when(sstuGroupScheduleParser.parse(group2Path)).thenReturn(group2ScheduleDays);
        when(sstuGroupScheduleParser.parse(group3Path)).thenReturn(group3ScheduleDays);
        when(sstuGroupScheduleParser.parse(group4Path)).thenReturn(group4ScheduleDays);

    }
*/
}
