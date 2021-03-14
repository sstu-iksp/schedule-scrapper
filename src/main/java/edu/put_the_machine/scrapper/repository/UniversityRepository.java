package edu.put_the_machine.scrapper.repository;

import edu.put_the_machine.scrapper.model.University;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UniversityRepository extends CrudRepository<University, Long> {
    boolean existsByName(String name);
    Optional<University> findByName(String name);
}
