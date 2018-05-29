package ru.spbau.des.chat.messenger;

import java.io.Closeable;

public interface Chat extends Closeable {
    void sendMessage(String message) throws ChatException;

    void subscribe(ChatListener listener);

    void unsubscribe(ChatListener listener);
}
