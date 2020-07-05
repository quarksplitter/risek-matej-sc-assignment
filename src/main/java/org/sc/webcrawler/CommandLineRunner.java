package org.sc.webcrawler;

import org.sc.webcrawler.engine.*;
import org.sc.webcrawler.messages.DefaultMessagePrinter;
import org.sc.webcrawler.messages.DefaultMessagesProvider;
import org.sc.webcrawler.messages.MessagePrinter;
import org.sc.webcrawler.messages.Messages;
import org.sc.webcrawler.util.trimmer.DefaultJsLibraryUrlTrimmer;
import org.sc.webcrawler.util.trimmer.SemVerJsLibraryUrlTrimmer;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CommandLineRunner {

    private final MessagePrinter messagePrinter = new DefaultMessagePrinter(
            new DefaultMessagesProvider(Map.of(
                    Messages.ISSUE_CONNECTING, "Issue connecting to web page.",
                    Messages.SEARCH_ENGINE_NO_RESULT_FOUND, "Search string does NOT produce any results",
                    Messages.ENTER_SEARCH_STRING, "Enter search string"
            ))
    );
    private final SearchEngineTopResultSupplier searchEngineTopResultSupplier = new JSoupSearchEngineTopResultSupplier();
    private final WebPageJsLibraryScraper webPageJsLibraryScraper = new JSoapWebPageJsLibraryScraper(
            new SemVerJsLibraryUrlTrimmer(
                    new DefaultJsLibraryUrlTrimmer()
            )
    );

    private final WebCrawler webCrawler = new DefaultWebCrawler(
            searchEngineTopResultSupplier,
            webPageJsLibraryScraper,
            messagePrinter
    );

    public static void main(String[] args) {
        new CommandLineRunner().run(args);
    }

    private void run(final String[] args) {
        if (args.length < 1) {
            messagePrinter.printInfoMessage(Messages.ENTER_SEARCH_STRING);
            System.exit(1);
        }

        String searchString = args[0];

        final String urlEncodedSearchString = URLEncoder.encode(searchString, StandardCharsets.UTF_8);

        try {
            webCrawler.getTopNJavaScriptLibrariesForSearchString(5, urlEncodedSearchString)
                    .stream()
                    .map(result -> result.getLibraryName() + " " + result.getFrequency())
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
