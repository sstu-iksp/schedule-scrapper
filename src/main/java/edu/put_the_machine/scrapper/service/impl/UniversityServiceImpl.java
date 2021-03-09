package edu.put_the_machine.scrapper.service.impl;

import edu.put_the_machine.scrapper.model.University;
import edu.put_the_machine.scrapper.repository.UniversityRepository;
import edu.put_the_machine.scrapper.service.interfaces.UniversityService;
import org.springframework.stereotype.Service;

@Service
public class UniversityServiceImpl implements UniversityService {
    private final UniversityRepository universityRepository;

    public UniversityServiceImpl(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    @Override
    public Iterable<University> getUniversities() {
        return universityRepository.findAll();
    }
}
