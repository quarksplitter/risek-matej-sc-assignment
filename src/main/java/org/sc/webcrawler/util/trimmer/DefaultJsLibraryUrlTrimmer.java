package org.sc.webcrawler.util.trimmer;

public class DefaultJsLibraryUrlTrimmer implements JsLibraryUrlTrimmer {

    @Override
    public String trim(final String url) {
        return url
                .replaceAll(".*/", "")
                .replaceAll("(\\.min)*\\.js(\\?.*)*$", "");
    }

}
