package org.sc.webcrawler.util.trimmer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SemVerJsLibraryUrlTrimmerTest {

    @Mock
    private JsLibraryUrlTrimmer delegate;

    private JsLibraryUrlTrimmer victim;

    @BeforeEach
    void setup() {
        when(delegate.trim(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());
        victim = new SemVerJsLibraryUrlTrimmer(delegate);
    }

    @Test
    public void invokesDelegateToObtainTrimmedLibraryName() {
        victim.trim("CALL_DELEGATE");

        verify(delegate).trim("CALL_DELEGATE");
    }

    private static Stream<Arguments> libraryNameSemVer() {
        return Stream.of(
                Arguments.of("test-1.0.0", "test"),
                Arguments.of("blah-blah", "blah-blah"),
                Arguments.of("version1.0.0", "version")
        );
    }

    @ParameterizedTest
    @MethodSource("libraryNameSemVer")
    public void shouldRemoveSemVer(String libraryName, String expectedResult) {
        final String actual = victim.trim(libraryName);

        assertThat(actual).isEqualTo(expectedResult);
    }

}
