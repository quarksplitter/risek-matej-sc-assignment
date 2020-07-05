package org.sc.webcrawler.engine;

import java.io.IOException;
import java.util.stream.Stream;

public interface SearchEngineTopResultSupplier {
    Stream<String> getTopResultsFor(String searchString) throws IOException;
}
