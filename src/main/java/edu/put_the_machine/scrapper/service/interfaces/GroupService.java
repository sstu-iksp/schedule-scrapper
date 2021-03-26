package edu.put_the_machine.scrapper.service.interfaces;

import edu.put_the_machine.scrapper.model.Group;
import edu.put_the_machine.scrapper.model.University;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    List<Group> getGroupsByUniversityId(Long universityId, Pageable pageable);
    Optional<Group> findByUniversityIdAndName(Long universityId, String name);
    Group createGroup(University university, String name);
}
