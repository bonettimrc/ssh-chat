package com.example;

import java.io.IOException;

public class User {
    private Screen chatCommand;
    String userName;
    private int ColorIndex;
    private Room room;

    public User(Screen chatCommand, Room room) {
        this.chatCommand = chatCommand;
        this.room = room;
        this.chatCommand.setUser(this);
        ColorIndex = (int) ((Math.random() * (36 - 31)) + 31);
    }

    public void onMessageRecieved(Message message) {
        try {
            chatCommand.writeLine(message.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void onMessageSent(Message message) {
        room.onMessage(message);
    }

    public void destroy() {
        room.removeUser(this);
    }

    public int getColorIndex() {
        return ColorIndex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
