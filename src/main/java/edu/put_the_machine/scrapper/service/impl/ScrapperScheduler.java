package edu.put_the_machine.scrapper.service.impl;

import edu.put_the_machine.scrapper.service.interfaces.ScrapperService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScrapperScheduler {
    private final ScrapperService scrapperService;

    public ScrapperScheduler(ScrapperService scrapperService) {
        this.scrapperService = scrapperService;
    }

    @Scheduled(
            cron =  "${scrapper.schedule.cron.seconds} ${scrapper.schedule.cron.minutes} " +
                    "${scrapper.schedule.cron.hour} ${scrapper.schedule.cron.month-day} " +
                    "${scrapper.schedule.cron.month} ${scrapper.schedule.cron.week-day} "
    )
    public void scheduleScrapping() {
        scrapperService.scrap();
    }
}
