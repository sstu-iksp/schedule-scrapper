package edu.put_the_machine.scrapper.service.interfaces;

import edu.put_the_machine.scrapper.model.University;

import java.util.Optional;


public interface UniversityService {
    Iterable<University> getUniversities();
    Optional<University> findByName(String name);
}
