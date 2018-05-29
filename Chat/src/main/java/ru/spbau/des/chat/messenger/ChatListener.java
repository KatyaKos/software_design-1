package ru.spbau.des.chat.messenger;

public interface ChatListener {
    void onMessage(ChatMessage message);

    void onConnected();

    void onDisconnected();

    void onError(String message);
}
