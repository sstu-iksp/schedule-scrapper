package edu.put_the_machine.scrapper.services.parsers;

import edu.put_the_machine.scrapper.exceptions.ParserException;
import edu.put_the_machine.scrapper.model.dto.*;
import edu.put_the_machine.scrapper.services.ParserServiceTest;
import edu.put_the_machine.scrapper.services.UrlToPageResolver;
import edu.put_the_machine.scrapper.services.impl.parsers.sstu.SstuGroupScheduleParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class SstuGroupScheduleScheduleParserTest extends ParserServiceTest {
    private @Mock UrlToPageResolver urlToPageResolver;
    private @InjectMocks SstuGroupScheduleParser parser;

    @BeforeEach
    public void init() {
        String universityName = "SSTU";
        ReflectionTestUtils.setField(parser, "universityName", universityName);
    }

    @Test
    public void whenParseANotExistingResourceThenThrowException() {
        assertThrows(ParserException.class, () -> parser.parse("null.null"));
    }

    @Test
    public void parseGroup0() throws IOException {
        String jsonResultPath = "src/test/recourses/parsers_tests_res/sstu/group0/sstuGroup0ScheduleJson.json";
        String rawDataPath = "src/test/recourses/parsers_tests_res/sstu/group0/sstuGroup0Schedule.html";
        when(urlToPageResolver.getBodyAsString(rawDataPath)).thenReturn(getFileContent(rawDataPath));

        List<ScheduleDayDto> returnedScheduleDays = parser.parse(rawDataPath);
        List<ScheduleDayDto> expectedScheduleDays = getExpectedScheduleDaysFromJsonFile(jsonResultPath);

        assertEquals(expectedScheduleDays, returnedScheduleDays);
    }

    @Test
    public void parseGroup1() throws IOException {
        String jsonResultPath = "src/test/recourses/parsers_tests_res/sstu/group1/sstuGroup1ScheduleJson.json";
        String rawDataPath = "src/test/recourses/parsers_tests_res/sstu/group1/sstuGroup1Schedule.html";
        when(urlToPageResolver.getBodyAsString(rawDataPath)).thenReturn(getFileContent(rawDataPath));

        List<ScheduleDayDto> returnedScheduleDays = parser.parse(rawDataPath);
        List<ScheduleDayDto> expectedScheduleDays = getExpectedScheduleDaysFromJsonFile(jsonResultPath);

        assertEquals(expectedScheduleDays, returnedScheduleDays);
    }

    @Test
    public void parseGroup2() throws IOException {
        String jsonResultPath = "src/test/recourses/parsers_tests_res/sstu/group2/sstuGroup2ScheduleJson.json";
        String rawDataPath = "src/test/recourses/parsers_tests_res/sstu/group2/sstuGroup2Schedule.html";
        when(urlToPageResolver.getBodyAsString(rawDataPath)).thenReturn(getFileContent(rawDataPath));

        List<ScheduleDayDto> returnedScheduleDays = parser.parse(rawDataPath);
        List<ScheduleDayDto> expectedScheduleDays = getExpectedScheduleDaysFromJsonFile(jsonResultPath);

        assertEquals(expectedScheduleDays, returnedScheduleDays);
    }
}
