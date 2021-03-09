package edu.put_the_machine.scrapper.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import instruments.ObjectMapperWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class ParserServiceTest {
    protected final ObjectMapperWrapper objectMapper = new ObjectMapperWrapper(new ObjectMapper());

    @BeforeEach
    public void parserServiceInit() {
        objectMapper.registerModule(new KotlinModule());
        objectMapper.registerModule(new JavaTimeModule());
    }
/*
    @NotNull
    protected List<ScheduleDayDto> getExpectedScheduleDaysFromJsonFile(String jsonPath) {
        return Arrays.asList(
                objectMapper.readValue(
                        new File(jsonPath),
                        ScheduleDayDto[].class
                )
        );
    }

    protected Document getHtmlDocument(String path) throws IOException {
        return Jsoup.parse(
                Files.readString(
                        Path.of(path)
                )
        );
    }*/
}
