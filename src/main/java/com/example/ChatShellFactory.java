package com.example;

import java.io.IOException;
import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;

import org.apache.sshd.server.shell.ShellFactory;

public class ChatShellFactory implements ShellFactory {
    private Room room;

    public ChatShellFactory(Room room) {
        this.room = room;
    }

    @Override
    public Command createShell(ChannelSession channel) throws IOException {
        ChatCommand chatCommand = new ChatCommand();
        User user = new User(chatCommand, room);
        room.addUser(user);
        return chatCommand;
    }

}
