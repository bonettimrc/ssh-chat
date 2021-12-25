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

    public void onChatCommadReadLine(String string, String userName) {
        System.out.println(string);
        for (ChatCommand chatCommand : chatCommands) {
            try {
                chatCommand.writeLine("\u001b[3"
                        .concat(Integer.toString(1 + (int) Math.abs(Math.floorMod(userName.hashCode(), 7))))
                        .concat("m").concat(userName).concat("\u001b[0m").concat(":").concat(string));
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