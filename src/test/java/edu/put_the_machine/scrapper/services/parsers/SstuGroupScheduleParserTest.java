package edu.put_the_machine.scrapper.services.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.put_the_machine.scrapper.model.dto.*;
import edu.put_the_machine.scrapper.exceptions.ParserException;
import instruments.ObjectMapperWrapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import edu.put_the_machine.scrapper.services.UrlToPageResolver;
import edu.put_the_machine.scrapper.services.impl.parsers.sstu.SstuGroupScheduleParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class SstuGroupScheduleParserTest {
    private final ObjectMapperWrapper objectMapper = new ObjectMapperWrapper(new ObjectMapper());
    private @Mock UrlToPageResolver urlToPageResolver;
    private SstuGroupScheduleParser parser;

    @BeforeEach
    public void init() {
        String universityName = "SSTU";
        parser = new SstuGroupScheduleParser(urlToPageResolver, universityName);
    }

    @Test
    public void whenParseANotExistingResourceThenThrowException() {
        assertThrows(ParserException.class, () -> parser.parse("null.null"));
    }

    @Test
    public void parse() throws IOException {
        String jsonResultPath = "src/test/recourses/parsers_tests_res/sstu/sstuGroup0ScheduleJson.json";
        String rawDataPath = "src/test/recourses/parsers_tests_res/sstu/sstuGroup0Schedule.html";
        when(urlToPageResolver.getHtmlTextFromUrl(rawDataPath)).thenReturn(Files.readString(Path.of(rawDataPath)));

        List<ScheduleDayDto> returnedScheduleDays = parser.parse(rawDataPath);
        List<ScheduleDayDto> expectedScheduleDays = getExpectedScheduleDays(jsonResultPath);

        assertEquals(expectedScheduleDays, returnedScheduleDays);
    }

    @NotNull
    private List<ScheduleDayDto> getExpectedScheduleDays(String jsonResultPath) {
        return Arrays.asList(
                objectMapper.readValue(
                        new File(jsonResultPath),
                        ScheduleDayDto[].class
                )
        );
    }
}
