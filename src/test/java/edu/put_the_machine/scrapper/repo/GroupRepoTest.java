package edu.put_the_machine.scrapper.repo;

import edu.put_the_machine.scrapper.model.Group;
import edu.put_the_machine.scrapper.model.University;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroupRepoTest {
    private @Autowired GroupRepo groupRepo;
    private @Autowired UniversityRepo universityRepo;

    @Test
    public void findByUniversityId() {
        University universitySearchCriteria = createUniversity("SSTU");
        University anotherUniversity = createUniversity("SGU");
        Group expectedGroup1 = createGroup("Б-ПИНЖ-31", universitySearchCriteria);
        Group expectedGroup2 = createGroup("Б2-ИФСТ-31", universitySearchCriteria);
        createGroup("some SGU group", anotherUniversity);

        List<Group> returnedGroups = groupRepo.findByUniversityId(universitySearchCriteria.getId());

        assertAll(
                () -> assertEquals(2, returnedGroups.size()),
                () -> assertThat(returnedGroups, containsInAnyOrder(expectedGroup1, expectedGroup2))
        );
    }

    private University createUniversity(String name) {
        University university = new University(name);
        return universityRepo.save(university);
    }

    private Group createGroup(String name, University university) {
        Group group = new Group(name, university);
        return groupRepo.save(group);
    }
}