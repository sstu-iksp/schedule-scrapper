package edu.put_the_machine.scrapper.service.interfaces;

import edu.put_the_machine.scrapper.model.Group;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupService {
    List<Group> getGroupsByUniversityId(Long universityId, Pageable pageable);
}
