package edu.put_the_machine.scrapper.services.parsers;

import edu.put_the_machine.scrapper.model.dto.parser.dto.GroupLessons;
import edu.put_the_machine.scrapper.model.dto.parser.dto.UniversityLessons;
import edu.put_the_machine.scrapper.services.ParserServiceTest;
import edu.put_the_machine.scrapper.service.impl.parsers.sstu.SstuScheduleParser;
import edu.put_the_machine.scrapper.service.interfaces.parser.GroupScheduleParser;
import edu.put_the_machine.scrapper.service.interfaces.parser.JsoupHelper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;


public class SstuScheduleParserTest extends ParserServiceTest {
    private final String pathToSchedulePage = "src/test/recourses/parsers_tests_res/sstu/groupsPageHtml.html";
    private final String universityName = "SSTU";
    private @Mock JsoupHelper jsoupHelper;
    private @Mock GroupScheduleParser sstuGroupScheduleParser;
    private SstuScheduleParser sstuScheduleParser;
    private GroupLessons group0Lessons;
    private GroupLessons group1Lessons;
    private GroupLessons group2Lessons;
    private GroupLessons group3Lessons;
    private GroupLessons group4Lessons;


    @BeforeEach
    public void init() {
        String jsonResultPath0 = "src/test/recourses/parsers_tests_res/sstu/group0/sstuGroup0ScheduleJson.json";
        String jsonResultPath1 = "src/test/recourses/parsers_tests_res/sstu/group1/sstuGroup1ScheduleJson.json";
        String jsonResultPath2 = "src/test/recourses/parsers_tests_res/sstu/group2/sstuGroup2ScheduleJson.json";
        String jsonResultPath3 = "src/test/recourses/parsers_tests_res/sstu/group3WithSubgroups/sstuGroup3ScheduleJson.json";
        String jsonResultPath4 = "src/test/recourses/parsers_tests_res/sstu/group4WithEmptyCells/sstuGroup4ScheduleJson.json";
        group0Lessons = getExpectedScheduleDaysFromJsonFile(jsonResultPath0);
        group1Lessons = getExpectedScheduleDaysFromJsonFile(jsonResultPath1);
        group2Lessons = getExpectedScheduleDaysFromJsonFile(jsonResultPath2);
        group3Lessons = getExpectedScheduleDaysFromJsonFile(jsonResultPath3);
        group4Lessons = getExpectedScheduleDaysFromJsonFile(jsonResultPath4);

        sstuScheduleParser = new SstuScheduleParser(jsoupHelper, sstuGroupScheduleParser, pathToSchedulePage, universityName);
    }

    @Test
    void parse() throws IOException {
        prepareMocks();

        UniversityLessons returnedUniversityLessons = sstuScheduleParser.parse();
        List<GroupLessons> expectedGroupsLessons = getExpectedGroupLessons();

        assertAll(
                () -> assertEquals(universityName, returnedUniversityLessons.getUniversityName()),
                () -> assertEquals(expectedGroupsLessons.size(), returnedUniversityLessons.getGroupsLessons().size()),
                () -> assertTrue(expectedGroupsLessons.containsAll(returnedUniversityLessons.getGroupsLessons())),
                () -> assertTrue(returnedUniversityLessons.getGroupsLessons().containsAll(expectedGroupsLessons)),
                () -> assertFalse(returnedUniversityLessons.getGroupsLessons().contains(null))
        );
        //Parser has to find only group hrefs.
        verify(sstuGroupScheduleParser, never()).parse(contains("static"));
    }

    @NotNull
    private List<GroupLessons> getExpectedGroupLessons() {
        return List.of(group0Lessons, group1Lessons, group2Lessons, group3Lessons, group4Lessons);
    }

    private void prepareMocks() throws IOException {
        String group0Path = pathToSchedulePage + "/group0";
        String group1Path = pathToSchedulePage + "/group1";
        String group2Path = pathToSchedulePage + "/group2";
        String group3Path = pathToSchedulePage + "/group3WithSubgroups";
        String group4Path = pathToSchedulePage + "/group4WithEmptyCells";

        when(jsoupHelper.getDocumentFromPath(pathToSchedulePage)).thenReturn(getHtmlDocument(pathToSchedulePage));
        when(sstuGroupScheduleParser.parse(group0Path)).thenReturn(group0Lessons);
        when(sstuGroupScheduleParser.parse(group1Path)).thenReturn(group1Lessons);
        when(sstuGroupScheduleParser.parse(group2Path)).thenReturn(group2Lessons);
        when(sstuGroupScheduleParser.parse(group3Path)).thenReturn(group3Lessons);
        when(sstuGroupScheduleParser.parse(group4Path)).thenReturn(group4Lessons);

    }
}
