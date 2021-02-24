package edu.put_the_machine.scrapper.services.interfaces.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public interface JsoupHelper {
    Document getDocumentFromPath(String path);

    String getTextByQueryOrThrowException(Element element, String query);
}
