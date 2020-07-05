package org.sc.webcrawler.engine;

import org.sc.webcrawler.data.CrawlResult;
import org.sc.webcrawler.messages.MessagePrinter;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.sc.webcrawler.messages.Messages.ISSUE_CONNECTING;

public class DefaultWebCrawler implements WebCrawler {

    private final SearchEngineTopResultSupplier searchEngineTopResultSupplier;
    private final WebPageJsLibraryScraper webPageJsLibraryScraper;
    private final MessagePrinter messagePrinter;

    public DefaultWebCrawler(
            final SearchEngineTopResultSupplier searchEngineTopResultSupplier,
            final WebPageJsLibraryScraper webPageJsLibraryScraper,
            final MessagePrinter messagePrinter
    ) {
        this.searchEngineTopResultSupplier = searchEngineTopResultSupplier;
        this.webPageJsLibraryScraper = webPageJsLibraryScraper;
        this.messagePrinter = messagePrinter;
    }

    @Override
    public Collection<CrawlResult> getTopNJavaScriptLibrariesForSearchString(final int n, final String searchString) throws IOException {
        return searchEngineTopResultSupplier.getTopResultsFor(searchString)
                .parallel()
                .flatMap(url -> {
                    try {
                        return webPageJsLibraryScraper.scrapeJsLibrariesFrom(url);
                    } catch (Exception e) {
                        messagePrinter.printInfoMessage(ISSUE_CONNECTING);
                        return Stream.empty();
                    }
                })
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(n)
                .map(CrawlResult::from)
                .collect(Collectors.toList());
    }

}
