package org.sc.webcrawler.messages;

public class DefaultMessagePrinter implements MessagePrinter {

    private final MessagesProvider messagesProvider;

    public DefaultMessagePrinter(final MessagesProvider messagesProvider) {
        this.messagesProvider = messagesProvider;
    }

    @Override
    public void printInfoMessage(final String messageId) {
        System.out.println(messagesProvider.getMessage(messageId));
    }
}
