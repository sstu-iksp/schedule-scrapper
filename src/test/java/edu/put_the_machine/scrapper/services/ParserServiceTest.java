package edu.put_the_machine.scrapper.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import edu.put_the_machine.scrapper.model.dto.parser.GroupLessons;
import instruments.ObjectMapperWrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@ExtendWith(MockitoExtension.class)
public abstract class ParserServiceTest {
    protected final ObjectMapperWrapper objectMapper = new ObjectMapperWrapper(new ObjectMapper());

    @BeforeEach
    public void parserServiceInit() {
        objectMapper.registerModule(new KotlinModule());
        objectMapper.registerModule(new JavaTimeModule());
    }

    protected GroupLessons getExpectedScheduleDaysFromJsonFile(String jsonPath) {
        return objectMapper.readValue(
                        new File(jsonPath),
                        GroupLessons.class
                );
    }

    protected Document getHtmlDocument(String path) throws IOException {
        return Jsoup.parse(
                Files.readString(
                        Path.of(path)
                )
        );
    }
}
