package edu.put_the_machine.scrapper.services.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import edu.put_the_machine.scrapper.exceptions.ParserException;
import edu.put_the_machine.scrapper.model.dto.*;
import edu.put_the_machine.scrapper.services.UrlToPageResolver;
import edu.put_the_machine.scrapper.services.impl.parsers.sstu.SstuGroupScheduleParser;
import instruments.ObjectMapperWrapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

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
public class SstuGroupScheduleScheduleParserTest {
    private final ObjectMapperWrapper objectMapper = new ObjectMapperWrapper(new ObjectMapper());
    private @Mock UrlToPageResolver urlToPageResolver;
    private SstuGroupScheduleParser parser;

    @BeforeEach
    public void init() {
        objectMapper.registerModule(new KotlinModule());
        objectMapper.registerModule(new JavaTimeModule());
        String universityName = "SSTU";
        parser = new SstuGroupScheduleParser(urlToPageResolver);
        ReflectionTestUtils.setField(parser, "universityName", universityName);
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
