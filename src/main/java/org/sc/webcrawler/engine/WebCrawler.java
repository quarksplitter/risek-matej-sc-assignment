package org.sc.webcrawler.engine;

import org.sc.webcrawler.data.CrawlResult;

import java.io.IOException;
import java.util.Collection;

public interface WebCrawler {
    Collection<CrawlResult> getTopNJavaScriptLibrariesForSearchString(int n, String searchString) throws IOException;
}
