package edu.put_the_machine.scrapper.controller;

import edu.put_the_machine.scrapper.model.University;
import edu.put_the_machine.scrapper.service.interfaces.UniversityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class UniversityController {
    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping("universities")
    public Iterable<University> getUniversities() {
        return universityService.getUniversities();
    }
}
