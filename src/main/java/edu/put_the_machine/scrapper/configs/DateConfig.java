package edu.put_the_machine.scrapper.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DateConfig {
    @Bean
    public LocalDate currentDate() {
        return LocalDate.now();
    }
}
