package edu.put_the_machine.scrapper.services.impl.parsers;

import edu.put_the_machine.scrapper.exceptions.ParserException;
import edu.put_the_machine.scrapper.services.interfaces.parser.JsoupHelper;
import edu.put_the_machine.scrapper.services.interfaces.UrlToPageResolver;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsoupHelperImpl implements JsoupHelper {
    private final UrlToPageResolver urlToPageResolver;

    public JsoupHelperImpl(UrlToPageResolver urlToPageResolver) {
        this.urlToPageResolver = urlToPageResolver;
    }

    @Override
    public Document getDocumentFromPath(String path) {
        try {
            String htmlText = urlToPageResolver.getBodyAsString(path);
            return Jsoup.parse(htmlText);
        } catch (IOException e) {
            throw new ParserException("Resource '" + path + "' is unavailable.", e);
        } catch (IllegalArgumentException e) {
            throw new ParserException("String '" + path + "' is not a valid path.", e);
        }
    }
}
