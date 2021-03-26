package edu.put_the_machine.scrapper.integration.controller;

import edu.put_the_machine.scrapper.model.Group;
import edu.put_the_machine.scrapper.model.Teacher;
import edu.put_the_machine.scrapper.model.University;
import instruments.helper.interfaces.MockMvcHelper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class TeacherControllerTest extends ControllerTest {
    @Test
    public void getById() {
        Teacher expectedTeacher = entitiesDbFactory.createTeacher("Бочаров Дмитрий Александрович");
        getByIdTest("teachers", () -> expectedTeacher, expectedTeacher.getId(), Teacher.class);
    }

    @Test
    public void getByUniversityId() {
        University university = entitiesDbFactory.createUniversity("SSTU");
        Teacher expectedTeacher1 = entitiesDbFactory.createTeacher("б-ПИНЖ-31", university);
        Teacher expectedTeacher2 = entitiesDbFactory.createTeacher("б2-ИФСТ-31", university);
        Teacher expectedTeacher3 = entitiesDbFactory.createTeacher("б-ПИНЖ-11", university);
        entitiesDbFactory.createTeacher("another teacher from another university");
        entitiesDbFactory.createTeacher("another another teacher from another university");

        List<Teacher> returnedTeachers = Arrays.asList(
                mockMvc.sendRequest(
                        get("/api/universities/" + university.getId() + "/teachers/"),
                        MockMvcHelper.expectJsonAndOkStatus()
                ).readBodyAsJsonByType(Teacher[].class)
        );

        assertAll(
                () -> assertEquals(3, returnedTeachers.size()),
                () -> assertThat(returnedTeachers, containsInAnyOrder(expectedTeacher1, expectedTeacher2, expectedTeacher3))
        );
    }
}
