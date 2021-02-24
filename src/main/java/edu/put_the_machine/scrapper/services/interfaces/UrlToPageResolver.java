package edu.put_the_machine.scrapper.services.interfaces;

import java.io.IOException;

public interface UrlToPageResolver {
    String getBodyAsString(String url) throws IOException;
}
