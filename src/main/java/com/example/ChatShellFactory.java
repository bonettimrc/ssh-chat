package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.example.commands.Interaction;
import com.example.commands.Ping;

import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;

import org.apache.sshd.server.shell.ShellFactory;

public class ChatShellFactory implements ShellFactory {
    private ArrayList<ChatCommand> chatCommands;
    private ArrayList<com.example.commands.Command> commands;
    private final String COMMAND_PREFIX = "/";

    public ChatShellFactory() {
        this.chatCommands = new ArrayList<ChatCommand>();
        this.commands = new ArrayList<com.example.commands.Command>();
        this.commands.add(new Ping());
    }

    @Override
    public Command createShell(ChannelSession channel) throws IOException {
        ChatCommand chatCommand = new ChatCommand(this);
        chatCommands.add(chatCommand);
        return chatCommand;
    }

    public void onMessage(Message message) {
        String content = message.getContent();
        if (content.startsWith(COMMAND_PREFIX)) {
            String[] strings = content.substring(1).split(" ");
            String[] parameters = Arrays.copyOfRange(strings, 1, strings.length);
            String commandName = strings[0];
            Interaction interaction = new Interaction(message.getSenderChatCommand(), parameters, this);
            for (com.example.commands.Command command : commands) {
                if (command.getName().equalsIgnoreCase(commandName)) {
                    command.accept(interaction);
                }
            }
            return;
        }
        for (ChatCommand chatCommand : chatCommands) {
            chatCommand.onMessage(message);
        }
    }

    public void removeChatCommand(ChatCommand chatCommand) {
        chatCommands.remove(chatCommand);
    }

    public ArrayList<ChatCommand> getChatCommands() {
        return chatCommands;
    }
}
