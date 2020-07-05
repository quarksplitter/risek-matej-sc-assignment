package org.sc.webcrawler.messages;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@ExtendWith(MockitoExtension.class)
class DefaultMessagePrinterTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOutputStream = System.out;

    @Mock
    private MessagesProvider messagesProvider;

    private MessagePrinter victim;

    @BeforeEach
    public void init() {
        System.setOut(new PrintStream(outputStream));
        victim = new DefaultMessagePrinter(messagesProvider);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOutputStream);
    }

    @Test
    public void obtainMessageFromMessageSupplier() {
        victim.printInfoMessage("message.id");

        Mockito.verify(messagesProvider).getMessage("message.id");
    }

    @Test
    public void printObtainedMessage() {
        Mockito.when(messagesProvider.getMessage("message.id")).thenReturn("This is the message");

        victim.printInfoMessage("message.id");

        Assertions.assertThat(outputStream.toString()).isEqualTo("This is the message\n");
    }

}
