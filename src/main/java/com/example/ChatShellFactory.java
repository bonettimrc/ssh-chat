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

    private String getColor(int i) {
        if (i == 0) {
            return "\u001b[0m";
        } else if (i < 8) {
            return "\u001b[3%dm".formatted(i);
        } else if (i < 16) {
            return "\u001b[9%dm".formatted(i % 8);
        } else {
            return "\u001b[0m";
        }
    }

    public void onChatCommadReadLine(String string, ChatCommand sender) {
        System.out.println(string);
        for (ChatCommand chatCommand : chatCommands) {
            try {
                chatCommand.writeLine("%s%s%s:%s".formatted(getColor(chatCommands.indexOf(sender) + 1), sender.userName,
                        getColor(0), string));
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