package com.example;

public class Message {
    private String content;
    private ChatCommand senderChatCommand;
    private Type type;

    public Message(String content, ChatCommand senderChatCommand, Type type) {
        this.content = content;
        this.senderChatCommand = senderChatCommand;
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
                return "\u001B[%dm%s\u001B[0m:%s".formatted(senderChatCommand.getColorIndex(),
                        senderChatCommand.getUserName(),
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

    public ChatCommand getSenderChatCommand() {
        return senderChatCommand;
    }

    public enum Type {
        PublicMessage, PrivateMessage, CommandMessage
    }
}
