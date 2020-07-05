package org.sc.webcrawler.util.trimmer;

public class QueryStringTrimmer implements JsLibraryUrlTrimmer {
    @Override
    public String trim(final String url) {
        return url.replaceAll("(\\?.*$)", "");
    }
}
