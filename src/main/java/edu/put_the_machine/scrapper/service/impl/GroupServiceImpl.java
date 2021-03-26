package edu.put_the_machine.scrapper.service.impl;

import edu.put_the_machine.scrapper.model.Group;
import edu.put_the_machine.scrapper.model.University;
import edu.put_the_machine.scrapper.repository.GroupRepository;
import edu.put_the_machine.scrapper.service.interfaces.GroupService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> getGroupsByUniversityId(Long universityId, Pageable pageable) {
        return groupRepository.findByUniversityId(universityId, pageable);
    }

    @Override
    public Optional<Group> findByUniversityIdAndName(Long universityId, String name) {
        return groupRepository.findByUniversityIdAndName(universityId, name);
    }

    @Override
    public Group createGroup(University university, String name) {
        return groupRepository.save(new Group(name, university));
    }
}
