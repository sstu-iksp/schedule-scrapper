package edu.put_the_machine.scrapper.repo;

import edu.put_the_machine.scrapper.model.University;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UniversityRepo extends CrudRepository<University, Long>, PagingAndSortingRepository<University, Long> {

}
