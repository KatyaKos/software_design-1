package ru.spbau.des.chat.transport;

public interface TransportListener {
    void onMessage(TransportMessage message);

    void onConnected();

    void onDisconnected();

    void onError(String message);
}
