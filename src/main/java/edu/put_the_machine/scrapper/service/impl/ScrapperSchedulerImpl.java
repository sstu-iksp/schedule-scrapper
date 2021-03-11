package edu.put_the_machine.scrapper.service.impl;

import edu.put_the_machine.scrapper.service.interfaces.ScrapperScheduler;
import edu.put_the_machine.scrapper.service.interfaces.ScrapperService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScrapperSchedulerImpl implements ScrapperScheduler {
    private final ScrapperService scrapperService;

    public ScrapperSchedulerImpl(ScrapperService scrapperService) {
        this.scrapperService = scrapperService;
    }

    @Scheduled(
            cron =  "${scrapper.schedule.seconds} ${scrapper.schedule.minutes} " +
                    "${scrapper.schedule.hour} ${scrapper.schedule.month-day} " +
                    "${scrapper.schedule.month} ${scrapper.schedule.week-day} "
    )
    @Override
    public void scheduleScrapping() {
        scrapperService.scrap();
    }
}
