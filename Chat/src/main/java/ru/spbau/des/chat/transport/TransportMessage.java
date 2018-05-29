package ru.spbau.des.chat.transport;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TransportMessage {
    private final String text;
    private final String author;
    private final LocalDateTime time;

    private TransportMessage(String text, String author, LocalDateTime time,
                             boolean service) {
        this.text = text;
        this.author = author;
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    protected Protocol.Message toProtocolMessage() {
        return Protocol.Message.newBuilder()
                .setText(text)
                .setAuthor(author)
                .setTime(time.toEpochSecond(ZoneOffset.MIN))
                .build();
    }

    public static class Builder {
        private String text;
        private String author;
        private LocalDateTime time;
        private boolean service;

        private Builder() {}

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setTime(LocalDateTime time) {
            this.time = time;
            return this;
        }

        protected Builder loadProtocolMessage(Protocol.Message protocolMessage) {
            text = protocolMessage.getText();
            author = protocolMessage.getAuthor();
            time = LocalDateTime.ofEpochSecond(protocolMessage.getTime(), 0,
                    ZoneOffset.MIN);
            return this;
        }

        public TransportMessage build() {
            return new TransportMessage(text, author, time, service);
        }
    }
}
