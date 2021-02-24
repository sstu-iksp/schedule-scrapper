package edu.put_the_machine.scrapper.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import edu.put_the_machine.scrapper.services.UrlToPageResolver;

@Service
public class UrlToPageResolverImpl implements UrlToPageResolver {
    private final RestTemplate restTemplate;

    public UrlToPageResolverImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getBodyAsString(String url) {
        return restTemplate.getForObject(url, String.class);
    }
}
