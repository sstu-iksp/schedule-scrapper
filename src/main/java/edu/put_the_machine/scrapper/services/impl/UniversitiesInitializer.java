package edu.put_the_machine.scrapper.services.impl;

import edu.put_the_machine.scrapper.model.University;
import edu.put_the_machine.scrapper.repo.UniversityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UniversitiesInitializer implements CommandLineRunner {
    private final UniversityRepo universityRepo;
    private final Map<String, String> universitiesNames;

    @Autowired
    public UniversitiesInitializer(UniversityRepo universityRepo, Map<String, String> universitiesNames) {
        this.universityRepo = universityRepo;
        this.universitiesNames = universitiesNames;
    }

    @Override
    public void run(String... args) {
        for (String name : universitiesNames.values()) {
            saveIfNotExist(name);
        }
    }

    private void saveIfNotExist(String name) {
        if (!universityRepo.existsByName(name)) {
            University university = new University(name);
            universityRepo.save(university);
        }
    }
}
