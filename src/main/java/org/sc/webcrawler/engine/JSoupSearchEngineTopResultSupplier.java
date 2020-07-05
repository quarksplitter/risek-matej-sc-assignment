package org.sc.webcrawler.engine;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.stream.Stream;

public class JSoupSearchEngineTopResultSupplier implements SearchEngineTopResultSupplier {
    @Override
    public Stream<String> getTopResultsFor(final String searchString) throws IOException {
        return Jsoup.connect("https://www.google.com/search?q=" + searchString)
                .get()
                .select("div[data-async-context^=query]")
                .select("a")
                .not("[class]")
                .stream()
                .map(element -> element.attr("href"));
    }
}
