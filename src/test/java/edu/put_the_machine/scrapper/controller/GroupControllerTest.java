package edu.put_the_machine.scrapper.controller;

import edu.put_the_machine.scrapper.model.Group;
import edu.put_the_machine.scrapper.model.University;
import instruments.factory.interfaces.UniversityDbFactory;
import instruments.helper.interfaces.MockMvcHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class GroupControllerTest extends ControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private @Autowired UniversityDbFactory universityDbFactory;

    @Test
    public void getById() {
        Group expectedGroup = universityDbFactory.createGroup("б-ПИНЖ-31");

        Group returnedGroup = mockMvc.sendRequest(
                get("/api/groups/" + expectedGroup.getId()),
                MockMvcHelper.expectJsonAndOkStatus()
        ).readBodyAsJsonByType(Group.class);

        assertAll(
                () -> assertNotNull(returnedGroup),
                () -> assertEquals(expectedGroup.getName(), returnedGroup.getName())
        );
    }

    @Test
    public void getByUniversityId() {
        University university = universityDbFactory.createUniversity("SSTU");
        Group expectedGroup1 = universityDbFactory.createGroup("б-ПИНЖ-31", university);
        Group expectedGroup2 = universityDbFactory.createGroup("б2-ИФСТ-31", university);
        Group expectedGroup3 = universityDbFactory.createGroup("б-ПИНЖ-11", university);
        universityDbFactory.createGroup("another group from another university");
        universityDbFactory.createGroup("another another group from another university");

        List<Group> returnedGroups = Arrays.asList(
                mockMvc.sendRequest(
                    get("/api/universities/" + university.getId() + "/groups/"),
                    MockMvcHelper.expectJsonAndOkStatus()
                ).readBodyAsJsonByType(Group[].class)
        );

        assertAll(
                () -> assertEquals(3, returnedGroups.size()),
                () -> assertThat(returnedGroups, containsInAnyOrder(expectedGroup1, expectedGroup2, expectedGroup3))
        );
    }
}