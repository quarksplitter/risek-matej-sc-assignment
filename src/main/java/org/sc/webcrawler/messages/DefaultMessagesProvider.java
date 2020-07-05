package org.sc.webcrawler.messages;

import java.util.Map;

public class DefaultMessagesProvider implements MessagesProvider {

    private final Map<String, String> messages;

    public DefaultMessagesProvider(final Map<String, String> messages) {
        this.messages = messages;
    }

    @Override
    public String getMessage(final String messageId) {
        return messages.getOrDefault(messageId, messageId);
    }
}
