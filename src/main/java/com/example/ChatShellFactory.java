package com.example;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.shell.ShellFactory;

public class ChatShellFactory implements ShellFactory {
    ArrayList<ChatCommand> chatCommands;

    public ChatShellFactory() {
        super();
        chatCommands = new ArrayList<ChatCommand>();
    }

    public void onChatCommadReadLine(String string) {
        System.out.println(string);
        for (ChatCommand chatCommand : chatCommands) {
            try {
                chatCommand.writeLine(chatCommand.userName.concat(":").concat(string));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public Command createShell(ChannelSession channel) throws IOException {
        ChatCommand chatCommand = new ChatCommand(this);
        chatCommands.add(chatCommand);
        return chatCommand;
    }
}