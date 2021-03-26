package edu.put_the_machine.scrapper.controller;

import edu.put_the_machine.scrapper.model.Teacher;
import edu.put_the_machine.scrapper.service.interfaces.TeacherService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping(value = "/teachers/{teacherId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Teacher getTeacherById(@PathVariable("teacherId") Teacher teacher) {
        return teacher;
    }

    @GetMapping(value = "/universities/{universityId}/teachers", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Teacher> getTeachersByUniversity(@PathVariable("universityId") Long universityId, Pageable pageable) {
        return teacherService.getTeachersByUniversityId(universityId, pageable);
    }
}
