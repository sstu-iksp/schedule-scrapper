package edu.put_the_machine.scrapper.repository;

import edu.put_the_machine.scrapper.model.Group;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {
    List<Group> findByUniversityId(Long universityId, Pageable pageable);
    Optional<Group> findByUniversityIdAndName(Long universityId, String name);
}
