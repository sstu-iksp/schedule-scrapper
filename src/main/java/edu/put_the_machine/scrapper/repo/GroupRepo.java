package edu.put_the_machine.scrapper.repo;

import edu.put_the_machine.scrapper.model.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepo extends CrudRepository<Group, Long>, PagingAndSortingRepository<Group, Long> {
    List<Group> findByUniversityId(Long universityId);
}
