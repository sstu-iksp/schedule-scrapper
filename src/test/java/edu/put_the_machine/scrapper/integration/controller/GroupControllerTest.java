package edu.put_the_machine.scrapper.integration.controller;

import edu.put_the_machine.scrapper.model.Group;
import edu.put_the_machine.scrapper.model.University;
import instruments.factory.interfaces.EntitiesDbFactory;
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
    private @Autowired
    EntitiesDbFactory entitiesDbFactory;

    @Test
    public void getById() {
        Group expectedGroup = entitiesDbFactory.createGroup("б-ПИНЖ-31");

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
        University university = entitiesDbFactory.createUniversity("SSTU");
        Group expectedGroup1 = entitiesDbFactory.createGroup("б-ПИНЖ-31", university);
        Group expectedGroup2 = entitiesDbFactory.createGroup("б2-ИФСТ-31", university);
        Group expectedGroup3 = entitiesDbFactory.createGroup("б-ПИНЖ-11", university);
        entitiesDbFactory.createGroup("another group from another university");
        entitiesDbFactory.createGroup("another another group from another university");

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