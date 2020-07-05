package org.sc.webcrawler.data;

import java.util.Map;

public class CrawlResult {

    private final String libraryName;
    private final long frequency;

    public CrawlResult(final String libraryName, final long frequency) {
        this.libraryName = libraryName;
        this.frequency = frequency;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public long getFrequency() {
        return frequency;
    }

    public static CrawlResult from(Map.Entry<String, Long> mapEntry) {
        return new CrawlResult(mapEntry.getKey(), mapEntry.getValue());
    }

}
