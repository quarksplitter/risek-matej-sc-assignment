package org.sc.webcrawler.engine;

import org.jsoup.Jsoup;
import org.sc.webcrawler.util.trimmer.JsLibraryUrlTrimmer;
import org.sc.webcrawler.util.trimmer.QueryStringTrimmer;

import java.io.IOException;
import java.util.stream.Stream;

public class JSoapWebPageJsLibraryScraper implements WebPageJsLibraryScraper {

    private final JsLibraryUrlTrimmer jsLibraryUrlTrimmer;
    private final QueryStringTrimmer queryStringTrimmer;

    public JSoapWebPageJsLibraryScraper(final JsLibraryUrlTrimmer jsLibraryUrlTrimmer) {
        this.jsLibraryUrlTrimmer = jsLibraryUrlTrimmer;
        queryStringTrimmer = new QueryStringTrimmer();
    }

    @Override
    public Stream<String> scrapeJsLibrariesFrom(final String url) throws IOException {
        return Jsoup.connect(url).get()
                .select("script[src]")
                .stream()
                .map(element -> element.attr("src"))
                .map(queryStringTrimmer::trim)
                .filter(path -> path.endsWith(".js"))
                .map(jsLibraryUrlTrimmer::trim);
    }

}
