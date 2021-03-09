package edu.put_the_machine.scrapper.service.impl;

import edu.put_the_machine.scrapper.model.Group;
import edu.put_the_machine.scrapper.repository.GroupRepository;
import edu.put_the_machine.scrapper.service.interfaces.GroupService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepo;

    public GroupServiceImpl(GroupRepository groupRepo) {
        this.groupRepo = groupRepo;
    }

    @Override
    public List<Group> getGroupsByUniversityId(Long universityId, Pageable pageable) {
        return groupRepo.findByUniversityId(universityId, pageable);
    }
}
