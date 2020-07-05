package org.sc.webcrawler.engine;

import java.io.IOException;
import java.util.stream.Stream;

public interface WebPageJsLibraryScraper {
    Stream<String> scrapeJsLibrariesFrom(String url) throws IOException;
}
