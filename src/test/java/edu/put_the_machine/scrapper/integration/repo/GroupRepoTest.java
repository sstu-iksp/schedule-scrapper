package edu.put_the_machine.scrapper.integration.repo;

import edu.put_the_machine.scrapper.model.Group;
import edu.put_the_machine.scrapper.model.University;
import edu.put_the_machine.scrapper.repository.GroupRepository;
import instruments.factory.interfaces.EntitiesDbFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


class GroupRepoTest extends RepositoryTest {
    private @Autowired
    GroupRepository groupRepo;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private @Autowired
    EntitiesDbFactory entitiesDbFactory;

    @Test
    public void findByUniversityId() {
        University universitySearchCriteria = entitiesDbFactory.createUniversity("SSTU");
        University anotherUniversity = entitiesDbFactory.createUniversity("SGU");
        Group expectedGroup1 = entitiesDbFactory.createGroup("Б-ПИНЖ-31", universitySearchCriteria);
        Group expectedGroup2 = entitiesDbFactory.createGroup("Б2-ИФСТ-31", universitySearchCriteria);
        entitiesDbFactory.createGroup("some SGU group", anotherUniversity);

        List<Group> returnedGroups = groupRepo.findByUniversityId(universitySearchCriteria.getId(), Pageable.unpaged());

        assertAll(
                () -> assertEquals(2, returnedGroups.size()),
                () -> assertThat(returnedGroups, containsInAnyOrder(expectedGroup1, expectedGroup2))
        );
    }
}