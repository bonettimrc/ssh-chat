package com.example;

public class Message {
    private String content;
    private User senderUser;
    private Type type;

    public Message(String content, User senderUser, Type type) {
        this.content = content;
        this.senderUser = senderUser;
        this.type = type;
    }

    public Message(String content) {
        this.content = content;
        this.type = Type.CommandMessage;
    }

    @Override
    public String toString() {
        switch (type) {
            case PublicMessage:
                return "\u001B[%dm%s\u001B[0m:%s".formatted(senderUser.getColorIndex(),
                        senderUser.getUserName(),
                        content);
            case PrivateMessage:
                // TODO
                return null;
            case CommandMessage:
                return "\u001B[1mconsole:%s\u001B[0m".formatted(
                        content);
            default:
                // TODO
                return "amongusError";
        }
    }

    public String getContent() {
        return content;
    }

    public User getSenderUser() {
        return senderUser;
    }

    public enum Type {
        PublicMessage, PrivateMessage, CommandMessage
    }
}
