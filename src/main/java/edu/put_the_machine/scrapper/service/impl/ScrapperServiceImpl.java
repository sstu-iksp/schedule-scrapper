package edu.put_the_machine.scrapper.service.impl;

import edu.put_the_machine.scrapper.service.interfaces.ScrapperService;
import org.springframework.stereotype.Service;

@Service
public class ScrapperServiceImpl implements ScrapperService {
    @Override
    public void scrap() {
        throw new RuntimeException("Not implemented yet");
    }
}
