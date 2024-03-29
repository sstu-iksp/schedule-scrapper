package edu.put_the_machine.scrapper.integration.repo;

import edu.put_the_machine.scrapper.model.Teacher;
import edu.put_the_machine.scrapper.model.University;
import edu.put_the_machine.scrapper.repository.TeacherRepository;
import instruments.factory.interfaces.EntitiesDbFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeacherRepoTest extends RepositoryTest {
    private @Autowired
    TeacherRepository teacherRepository;
    private @Autowired EntitiesDbFactory entitiesDbFactory;

    @Test
    public void findByUniversity() {
        University university = entitiesDbFactory.createUniversity("SSTU");
        Teacher expectedTeacher1 = entitiesDbFactory.createTeacher("Бочаров Дмитрий Александрович", university);
        Teacher expectedTeacher2 = entitiesDbFactory.createTeacher("Вагарина Наталия Сергеевна", university);
        entitiesDbFactory.createTeacher("another university teacher");

        List<Teacher> returnedTeachers = teacherRepository.findByUniversityId(university.getId(), Pageable.unpaged());

        assertAll(
                () -> assertEquals( 2, returnedTeachers.size()),
                () -> assertThat(returnedTeachers, containsInAnyOrder(expectedTeacher1, expectedTeacher2))
        );
    }
}
