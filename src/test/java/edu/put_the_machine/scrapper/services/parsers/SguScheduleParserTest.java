package edu.put_the_machine.scrapper.services.parsers;

import edu.put_the_machine.scrapper.model.dto.parser.GroupLessons;
import edu.put_the_machine.scrapper.model.dto.parser.UniversityLessons;
import edu.put_the_machine.scrapper.service.impl.parsers.sgu.SguScheduleParser;
import edu.put_the_machine.scrapper.service.interfaces.parser.GroupScheduleParser;
import edu.put_the_machine.scrapper.service.interfaces.parser.JsoupHelper;
import edu.put_the_machine.scrapper.services.ParserServiceTest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;


public class SguScheduleParserTest extends ParserServiceTest {
    private final String pathToSchedulePage = "src/test/recourses/parsers_tests_res/sgu/institutesPageHtml.html";
    private final String universityName = "SGU";
    private @Mock
    JsoupHelper jsoupHelper;
    private @Mock
    GroupScheduleParser sguGroupScheduleParser;
    private SguScheduleParser sguScheduleParser;
    private GroupLessons group0Lessons;
    private GroupLessons group1Lessons;
    private GroupLessons group2Lessons;


    @BeforeEach
    public void init() {
        String jsonResultPath0 = "src/test/recourses/parsers_tests_res/sgu/group0/sguGroup0ScheduleJson.json";
        String jsonResultPath1 = "src/test/recourses/parsers_tests_res/sgu/group1/sguGroup1ScheduleJson.json";
        String jsonResultPath2 = "src/test/recourses/parsers_tests_res/sgu/group2/sguGroup2ScheduleJson.json";
        group0Lessons = getExpectedScheduleDaysFromJsonFile(jsonResultPath0);
        group1Lessons = getExpectedScheduleDaysFromJsonFile(jsonResultPath1);
        group2Lessons = getExpectedScheduleDaysFromJsonFile(jsonResultPath2);

        sguScheduleParser = new SguScheduleParser(jsoupHelper, sguGroupScheduleParser, pathToSchedulePage, universityName);
    }

    @Test
    void parse() {
        prepareMocks();

        UniversityLessons returnedUniversityLessons = sguScheduleParser.parse();
        List<GroupLessons> expectedGroupsLessons = getExpectedGroupLessons();

        assertAll(
                () -> assertEquals(universityName, returnedUniversityLessons.getUniversityName()),
                () -> assertEquals(expectedGroupsLessons.size(), returnedUniversityLessons.getGroupsLessons().size()),
                () -> assertTrue(expectedGroupsLessons.containsAll(returnedUniversityLessons.getGroupsLessons())),
                () -> assertTrue(returnedUniversityLessons.getGroupsLessons().containsAll(expectedGroupsLessons)),
                () -> assertFalse(returnedUniversityLessons.getGroupsLessons().contains(null))
        );

        verify(sguGroupScheduleParser, never()).parse(contains("static"));
    }

    @NotNull
    private List<GroupLessons> getExpectedGroupLessons() {
        return List.of(group0Lessons, group1Lessons, group2Lessons);
    }

    private void prepareMocks() {
        String group0Path = pathToSchedulePage + "/group0";
        String group1Path = pathToSchedulePage + "/group1";
        String group2Path = pathToSchedulePage + "/group2";

        when(jsoupHelper.getDocumentFromPath(anyString())).thenAnswer((InvocationOnMock invocation) -> getHtmlDocument((String) invocation.getArguments()[0]));
        when(sguGroupScheduleParser.parse(group0Path)).thenReturn(group0Lessons);
        when(sguGroupScheduleParser.parse(group1Path)).thenReturn(group1Lessons);
        when(sguGroupScheduleParser.parse(group2Path)).thenReturn(group2Lessons);

    }
}