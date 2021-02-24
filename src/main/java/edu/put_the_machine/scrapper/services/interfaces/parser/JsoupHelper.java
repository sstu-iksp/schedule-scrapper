package edu.put_the_machine.scrapper.services.interfaces.parser;

import org.jsoup.nodes.Document;

public interface JsoupHelper {
    Document getDocumentFromPath(String path);
}
