package edu.put_the_machine.scrapper.repo;

import edu.put_the_machine.scrapper.model.University;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UniversityRepo extends CrudRepository<University, Long> {

    boolean existsByName(String name);
}
