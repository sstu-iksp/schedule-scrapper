package edu.put_the_machine.scrapper.service.impl;

import edu.put_the_machine.scrapper.model.University;
import edu.put_the_machine.scrapper.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Order(0)
public class UniversitiesInitializer implements CommandLineRunner {
    private final UniversityRepository universityRepository;
    private final Map<String, String> universitiesNames;

    @Autowired
    public UniversitiesInitializer(UniversityRepository universityRepository, Map<String, String> universitiesNames) {
        this.universityRepository = universityRepository;
        this.universitiesNames = universitiesNames;
    }

    @Override
    public void run(String... args) {
        for (String name : universitiesNames.values()) {
            saveIfNotExist(name);
        }
    }

    private void saveIfNotExist(String name) {
        if (!universityRepository.existsByName(name)) {
            University university = new University(name);
            universityRepository.save(university);
        }
    }
}
