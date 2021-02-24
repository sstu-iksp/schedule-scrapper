package edu.put_the_machine.scrapper.services;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import edu.put_the_machine.scrapper.services.impl.UrlToPageResolverImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlToPageResolverTest {
    private @Mock RestTemplate restTemplate;
    private @InjectMocks UrlToPageResolverImpl urlToPageResolver;

    @Test
    public void getHtmlStringFromUrl() throws IOException {
        String url = "localhost";
        String expectedResult = getExpectedResult();
        when(restTemplate.getForObject(url, String.class)).thenReturn(expectedResult);

        String returnedResult = urlToPageResolver.getBodyAsString(url);
        assertEquals(expectedResult, returnedResult);
    }

    private String getExpectedResult() throws IOException {
        String rawDataPath = "src/test/recourses/parsers_tests_res/sstu/sstuGroup1Schedule.html";
        return Files.readString(Path.of(rawDataPath));
    }
}
