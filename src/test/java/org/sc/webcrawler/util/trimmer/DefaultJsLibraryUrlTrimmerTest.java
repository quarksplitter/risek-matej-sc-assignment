package org.sc.webcrawler.util.trimmer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultJsLibraryUrlTrimmerTest {

    private final JsLibraryUrlTrimmer victim = new DefaultJsLibraryUrlTrimmer();

    private static Stream<Arguments> urlsWithQueryString() {
        return Stream.of(
                Arguments.of("bizible.js?ver=1.0.0", "?ver=1.0.0"),
                Arguments.of("bootstrap.min.js?someArgument=argument&anotherArgument=arg2", "?someArgument=argument&anotherArgument=arg2")
        );
    }

    @ParameterizedTest
    @MethodSource("urlsWithQueryString")
    public void shouldRemoveQueryString(String url, String queryString) {
        final String actual = victim.trim(url);

        assertThat(actual).doesNotContain(queryString);
    }

    private static Stream<Arguments> urlsWithExtensions() {
        return Stream.of(
                Arguments.of("bizible.js?ver=1.0.0", "bizible"),
                Arguments.of("bootstrap.min.js", "bootstrap")
        );
    }

    @ParameterizedTest
    @MethodSource("urlsWithExtensions")
    public void shouldRemoveExtensions(String url, String expectedResult) {
        final String actual = victim.trim(url);

        assertThat(actual).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> urlPaths() {
        return Stream.of(
                Arguments.of("//cdn.bizible.com/scripts/bizible.js", "bizible"),
                Arguments.of("/javascripts/mobile-menu.js", "mobile-menu"),
                Arguments.of("/javascripts/libs/tweenmax.min.js", "tweenmax"),
                Arguments.of("https://consent.cookiebot.com/uc.js", "uc")
        );
    }

    @ParameterizedTest
    @MethodSource("urlPaths")
    public void removePrefixedPath(String url, String library) {
        final String actual = victim.trim(url);

        assertThat(actual).startsWith(library);
    }

}
