package org.sc.webcrawler.util.trimmer;

public class SemVerJsLibraryUrlTrimmer implements JsLibraryUrlTrimmer {

    private final JsLibraryUrlTrimmer delegate;

    public SemVerJsLibraryUrlTrimmer(final JsLibraryUrlTrimmer delegate) {
        this.delegate = delegate;
    }

    @Override
    public String trim(final String url) {
        return delegate.trim(url).replaceAll("-?\\d+\\.\\d+\\.\\d+$", "");
    }

}
