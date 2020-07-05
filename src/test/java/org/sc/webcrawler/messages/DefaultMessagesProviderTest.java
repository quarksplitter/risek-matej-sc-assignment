package org.sc.webcrawler.messages;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultMessagesProviderTest {

    private final Map<String, String> messages = Map.of(
            "test.message.one", "This is value of test message One",
            "test.message.forty.two", "Answer to the Ultimate Question of Life, the Universe, and Everything"
    );

    private final DefaultMessagesProvider victim = new DefaultMessagesProvider(messages);

    @Test
    public void locateMessageByMessageIdAndReturnIt() {
        final String actualMessage = victim.getMessage("test.message.forty.two");

        assertThat(actualMessage).isEqualTo("Answer to the Ultimate Question of Life, the Universe, and Everything");
    }

    @Test
    public void ifMessageIdCannotBeFoundReturnProvidedMessageId() {
        final String actualMessage = victim.getMessage("unknown.message");

        assertThat(actualMessage).isEqualTo("unknown.message");
    }

}
