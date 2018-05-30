package ru.spbau.des.chat.messenger;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;
import ru.spbau.des.chat.transport.Transport;
import ru.spbau.des.chat.transport.TransportException;
import ru.spbau.des.chat.transport.TransportMessage;

import java.time.LocalDateTime;

public class MessengerTest {
    private static final String NAME = "we";
    private static final String TEXT = "foo";
    private final LocalDateTime TIME = LocalDateTime.now();
    private final TransportMessage TRANSPORT_MSG = TransportMessage.getBuilder()
            .setAuthor(NAME)
            .setText(TEXT)
            .setTime(TIME)
            .build();
    private final ChatMessage CHAT_MSG = ChatMessage.getBuilder()
            .setAuthor(NAME)
            .setText(TEXT)
            .setTime(TIME)
            .build();

    @Test
    public void createTest() throws TransportException {
        Transport transportMock = Mockito.mock(Transport.class);
        Messenger messenger = new Messenger(transportMock, NAME);
        ArgumentCaptor<TransportMessage> messageCaptor =
                ArgumentCaptor.forClass(TransportMessage.class);
        Mockito.verify(transportMock).send(messageCaptor.capture());
        Assert.assertEquals(NAME, messageCaptor.getValue().getAuthor());
        Assert.assertEquals("Connected", messageCaptor.getValue().getText());
    }

    @Test
    public void subscribeOnMessageTest() {
        ChatListener listenerMock = Mockito.mock(ChatListener.class);
        Transport transportMock = Mockito.mock(Transport.class);
        Messenger messenger = new Messenger(transportMock, NAME);
        messenger.subscribe(listenerMock);
        messenger.onMessage(TRANSPORT_MSG);
        Mockito.verify(listenerMock).onMessage(CHAT_MSG);
    }

    @Test
    public void unsubscribeTest() {
        ChatListener listenerMock = Mockito.mock(ChatListener.class);
        Transport transportMock = Mockito.mock(Transport.class);
        Messenger messenger = new Messenger(transportMock, NAME);
        messenger.subscribe(listenerMock);
        messenger.unsubscribe(listenerMock);
        messenger.onMessage(TRANSPORT_MSG);
        Mockito.verify(listenerMock, Mockito.never()).onMessage(Mockito.any());
    }

    @Test
    public void sendMessageTest() throws ChatException, TransportException {
        Transport transportMock = Mockito.mock(Transport.class);
        Messenger messenger = new Messenger(transportMock, NAME);
        ArgumentCaptor<TransportMessage> captor1 =
                ArgumentCaptor.forClass(TransportMessage.class);
        ArgumentCaptor<TransportMessage> captor2 =
                ArgumentCaptor.forClass(TransportMessage.class);
        InOrder order = Mockito.inOrder(transportMock);
        order.verify(transportMock).send(captor1.capture());
        messenger.sendMessage(TEXT);
        order.verify(transportMock).send(captor2.capture());
        Assert.assertEquals(NAME, captor2.getValue().getAuthor());
        Assert.assertEquals(TEXT, captor2.getValue().getText());
    }

    @Test
    public void onDisconnectedTest() {
        ChatListener listenerMock = Mockito.mock(ChatListener.class);
        Transport transportMock = Mockito.mock(Transport.class);
        Messenger messenger = new Messenger(transportMock, NAME);
        messenger.subscribe(listenerMock);
        messenger.onDisconnected();
        Mockito.verify(listenerMock).onDisconnected();
    }

    @Test
    public void onErrorTest() {
        ChatListener listenerMock = Mockito.mock(ChatListener.class);
        Transport transportMock = Mockito.mock(Transport.class);
        Messenger messenger = new Messenger(transportMock, NAME);
        messenger.subscribe(listenerMock);
        messenger.onError(TEXT);
        Mockito.verify(listenerMock).onError(TEXT);
    }

    @Test
    public void equalsTest() {
        Transport transportMock = Mockito.mock(Transport.class);
        Messenger messenger1 = new Messenger(transportMock, NAME);
        Messenger messenger2 = new Messenger(transportMock, NAME);
        Assert.assertNotEquals(messenger1, messenger2);
    }

    @Test
    public void multipleSubscribersTest() {
        ChatListener listener1 = Mockito.mock(ChatListener.class);
        ChatListener listener2 = Mockito.mock(ChatListener.class);
        Transport transportMock = Mockito.mock(Transport.class);
        Messenger messenger = new Messenger(transportMock, NAME);
        messenger.subscribe(listener1);
        messenger.subscribe(listener2);
        messenger.onMessage(TRANSPORT_MSG);
        Mockito.verify(listener1).onMessage(CHAT_MSG);
        Mockito.verify(listener2).onMessage(CHAT_MSG);
    }
}
