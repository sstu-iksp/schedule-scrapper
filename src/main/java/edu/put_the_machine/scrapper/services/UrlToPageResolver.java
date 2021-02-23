package edu.put_the_machine.scrapper.services;

import java.io.IOException;

public interface UrlToPageResolver {
    String getHtmlTextFromUrl(String url) throws IOException;
}
