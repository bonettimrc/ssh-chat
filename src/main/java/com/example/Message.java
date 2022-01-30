package com.example;

import java.util.function.Function;

import com.example.ansicontrolsequences.ControlSequence;

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
        Function<String, ControlSequence> sgr = ControlSequence.selectGraphicRendition;
        ControlSequence reset = sgr.apply("0");
        switch (type) {
            case PublicMessage:
                ControlSequence senderUserColor = sgr.apply(Integer.toString(senderUser.getColorIndex()));
                return String.format("%s%s%s:%s", senderUserColor.toString(), senderUser.getUserName(),
                        reset.toString(),
                        content);
            case PrivateMessage:
                // TODO: implement private messages style
                return null;
            case CommandMessage:
                ControlSequence bold = sgr.apply("1");
                // TODO: dynamic room name
                return String.format("%s%s%s:%s", bold.toString(), "Room", reset.toString(),
                        content);
            default:
                // TODO: this default can't really happen
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
