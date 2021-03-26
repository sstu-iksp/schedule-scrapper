package edu.put_the_machine.scrapper.controller;

import edu.put_the_machine.scrapper.model.Group;
import edu.put_the_machine.scrapper.service.interfaces.GroupService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping(value = "/groups/{groupId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Group getGroupById(@PathVariable("groupId") Group group) {
        return group;
    }

    @GetMapping(value = "/universities/{universityId}/groups", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Group> getGroupsByUniversityId(@PathVariable("universityId") Long universityId, Pageable pageable) {
        return groupService.getGroupsByUniversityId(universityId, pageable);
    }
}
