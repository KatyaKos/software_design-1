package ru.spbau.des.chat.transport;

import java.io.Closeable;

public interface Transport extends Closeable {
    void send(TransportMessage transportMessage) throws TransportException;

    void subscribe(TransportListener listener);

    void unsubscribe();
}
