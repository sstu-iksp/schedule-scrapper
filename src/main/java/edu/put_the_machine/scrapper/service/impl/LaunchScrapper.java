package edu.put_the_machine.scrapper.service.impl;

import edu.put_the_machine.scrapper.service.interfaces.ScrapperService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class LaunchScrapper implements CommandLineRunner {
    private final ScrapperService scrapperService;

    public LaunchScrapper(ScrapperService scrapperService) {
        this.scrapperService = scrapperService;
    }

    @Override
    public void run(String... args) {
        scrapperService.scrap();
    }
}
