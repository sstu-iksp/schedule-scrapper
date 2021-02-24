package edu.put_the_machine.scrapper.services.parsers;

import edu.put_the_machine.scrapper.model.dto.ScheduleDayDto;
import edu.put_the_machine.scrapper.services.GroupScheduleParser;
import edu.put_the_machine.scrapper.services.ParserServiceTest;
import edu.put_the_machine.scrapper.services.UrlToPageResolver;
import edu.put_the_machine.scrapper.services.impl.parsers.sstu.SstuScheduleParser;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SstuScheduleParserTest extends ParserServiceTest {
    private final String pathToSchedulePage = "src/test/recourses/parsers_tests_res/sstu/groupsPageHtml.html";
    private @Mock UrlToPageResolver urlToPageResolver;
    private @Mock GroupScheduleParser groupScheduleParser;
    private @InjectMocks SstuScheduleParser sstuScheduleParser;
    private List<ScheduleDayDto> group0ScheduleDays;
    private List<ScheduleDayDto> group1ScheduleDays;
    private List<ScheduleDayDto> group2ScheduleDays;


    @BeforeEach
    public void init() {
        String jsonResultPath0 = "src/test/recourses/parsers_tests_res/sstu/group0/sstuGroup0ScheduleJson.json";
        String jsonResultPath1 = "src/test/recourses/parsers_tests_res/sstu/group1/sstuGroup1ScheduleJson.json";
        String jsonResultPath2 = "src/test/recourses/parsers_tests_res/sstu/group2/sstuGroup2ScheduleJson.json";
        group0ScheduleDays = getExpectedScheduleDaysFromJsonFile(jsonResultPath0);
        group1ScheduleDays = getExpectedScheduleDaysFromJsonFile(jsonResultPath1);
        group2ScheduleDays = getExpectedScheduleDaysFromJsonFile(jsonResultPath2);

        ReflectionTestUtils.setField(sstuScheduleParser, "pathToSchedulePage", pathToSchedulePage);
    }

    @Test
    void parse() throws IOException {
        prepareMocks();

        List<ScheduleDayDto> returnedScheduleDays = sstuScheduleParser.parse();
        List<ScheduleDayDto> expectedScheduleDays = getExpectedScheduleDays();

        assertAll(
                () -> assertEquals(expectedScheduleDays.size(), returnedScheduleDays.size()),
                () -> assertTrue(expectedScheduleDays.containsAll(returnedScheduleDays) ),
                () -> assertTrue(returnedScheduleDays.containsAll(expectedScheduleDays) )
        );
    }

    @NotNull
    private List<ScheduleDayDto> getExpectedScheduleDays() {
        return Stream.of(group0ScheduleDays, group1ScheduleDays, group2ScheduleDays)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
    }

    private void prepareMocks() throws IOException {
        String group0Path = pathToSchedulePage + "/group0";
        String group1Path = pathToSchedulePage + "/group1";
        String group2Path = pathToSchedulePage + "/group2";


        when(urlToPageResolver.getBodyAsString(pathToSchedulePage)).thenReturn(getFileContent(pathToSchedulePage));
        when(groupScheduleParser.parse(group0Path)).thenReturn(group0ScheduleDays);
        when(groupScheduleParser.parse(group1Path)).thenReturn(group1ScheduleDays);
        when(groupScheduleParser.parse(group2Path)).thenReturn(group2ScheduleDays);
        when(groupScheduleParser.parse(any())).then((i) ->  fail("Was invoked parse method with unexpected path + " + i.getArgument(0)));
    }
}
