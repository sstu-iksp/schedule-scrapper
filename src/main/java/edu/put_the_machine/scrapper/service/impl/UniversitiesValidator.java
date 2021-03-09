package edu.put_the_machine.scrapper.service.impl;

import edu.put_the_machine.scrapper.exceptions.PropertiesValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UniversitiesValidator implements CommandLineRunner {
    private final Map<String, String> universitiesNames;

    @Autowired
    public UniversitiesValidator(Map<String, String> universitiesNames) {
        this.universitiesNames = universitiesNames;
    }

    @Override
    public void run(String... args){
        long allUniversitiesSize = universitiesNames.size();
        long uniqueUniversitiesSize = universitiesNames.values().stream().distinct().count();

        if (allUniversitiesSize != uniqueUniversitiesSize)
            throw new PropertiesValidationException("parser.university.name application property values have to be unique, but they are not.");
    }
}
