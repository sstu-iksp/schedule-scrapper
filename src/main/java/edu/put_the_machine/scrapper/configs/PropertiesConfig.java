package edu.put_the_machine.scrapper.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PropertiesConfig {

    @ConfigurationProperties(prefix = "parser.university.name", ignoreUnknownFields = false)
    @Bean
    public Map<String, String> universitiesNames() {
        return new HashMap<>();
    }
}
