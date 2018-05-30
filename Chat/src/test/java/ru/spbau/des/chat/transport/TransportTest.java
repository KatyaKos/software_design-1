package ru.spbau.des.chat.transport;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;

public class TransportTest {
    private static final int PORT = 12345;
    private static final String LOCALHOST = "127.0.0.1";
    private static final String NAME = "we";
    private static final String TEXT = "foo";
    private final LocalDateTime TIME = LocalDateTime.now();
    private final TransportMessage MESSAGE = TransportMessage.getBuilder()
            .setAuthor(NAME)
            .setText(TEXT)
            .setTime(TIME)
            .build();

    @Test
    public void sendTest() throws IOException, TransportException, InterruptedException {
        ServerTransport serverTransport = new ServerTransport(PORT);
        ClientTransport clientTransport = new ClientTransport(LOCALHOST, PORT);
        TransportListener listener = Mockito.mock(TransportListener.class);
        serverTransport.subscribe(listener);
        clientTransport.send(MESSAGE);
        Thread.sleep(1000);
        Mockito.verify(listener).onMessage(MESSAGE);
    }
}
