package org.sc.webcrawler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sc.webcrawler.data.CrawlResult;
import org.sc.webcrawler.engine.DefaultWebCrawler;
import org.sc.webcrawler.engine.SearchEngineTopResultSupplier;
import org.sc.webcrawler.engine.WebCrawler;
import org.sc.webcrawler.engine.WebPageJsLibraryScraper;
import org.sc.webcrawler.messages.MessagePrinter;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultWebCrawlerTest {

    @Mock
    private SearchEngineTopResultSupplier searchEngineTopResultSupplier;

    @Mock
    private WebPageJsLibraryScraper webPageJsLibraryScraper;

    @Mock
    private MessagePrinter messagePrinter;

    private WebCrawler victim;

    @BeforeEach
    void setup() {
        victim = new DefaultWebCrawler(
                searchEngineTopResultSupplier,
                webPageJsLibraryScraper,
                messagePrinter
        );
    }

    @Test
    public void callsSearchEngineSupplierToObtainTopResultsForProvidedSearchString() throws IOException {
        victim.getTopNJavaScriptLibrariesForSearchString(0, "TEST_STRING");

        verify(searchEngineTopResultSupplier).getTopResultsFor("TEST_STRING");
    }

    @Test
    public void ifSearchEngineResultsInNoMatchesReturnAnEmptyCollection() throws IOException {
        when(searchEngineTopResultSupplier.getTopResultsFor(any())).thenReturn(Stream.empty());

        final Collection<CrawlResult> actualResult = victim.getTopNJavaScriptLibrariesForSearchString(0, "");

        assertThat(actualResult).isEmpty();
    }

    // ... TDD doesn't play well with time limits :-)

}
