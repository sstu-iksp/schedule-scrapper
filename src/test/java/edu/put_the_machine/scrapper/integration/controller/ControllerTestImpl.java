package edu.put_the_machine.scrapper.integration.controller;

import edu.put_the_machine.scrapper.model.University;
import instruments.helper.interfaces.MockMvcHelper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class ControllerTestImpl extends ControllerTest {
    @Test
    public void getUniversities() {
        University university1 = entitiesDbFactory.createUniversity("1");
        University university2 = entitiesDbFactory.createUniversity("2");
        University university3 = entitiesDbFactory.createUniversity("3");

        List<University> returnedUniversities = Arrays.asList(
                mockMvc.sendRequest(
                        get("/api/universities/"),
                        MockMvcHelper.expectJsonAndOkStatus()
                ).readBodyAsJsonByType(University[].class)
        );

        assertAll(
                () -> assertEquals(3, returnedUniversities.size()),
                () -> assertThat(returnedUniversities, containsInAnyOrder(university1, university2, university3))
        );
    }
}
