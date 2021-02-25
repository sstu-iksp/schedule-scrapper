package edu.put_the_machine.scrapper.service.impl;

import edu.put_the_machine.scrapper.model.University;
import edu.put_the_machine.scrapper.repo.UniversityRepo;
import edu.put_the_machine.scrapper.service.interfaces.UniversityService;
import org.springframework.stereotype.Service;

@Service
public class UniversityServiceImpl implements UniversityService {
    private final UniversityRepo universityRepo;

    public UniversityServiceImpl(UniversityRepo universityRepo) {
        this.universityRepo = universityRepo;
    }

    @Override
    public Iterable<University> getUniversities() {
        return universityRepo.findAll();
    }
}
