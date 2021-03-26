package edu.put_the_machine.scrapper.integration.repo;


import edu.put_the_machine.scrapper.model.University;
import edu.put_the_machine.scrapper.repository.UniversityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UniversityRepoTest {
    private @Autowired
    UniversityRepository universityRepository;

    @Test
    public void existsByName() {
        String name = "SSTU";
        createUniversity(name);
        createUniversity("SGU");

        assertTrue(universityRepository.existsByName(name));
    }

    private void createUniversity(String name) {
        University university = new University(name);
        universityRepository.save(university);
    }
}
