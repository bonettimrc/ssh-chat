package com.example.commands;

import com.example.ChatCommand;
import com.example.Message;

public class Ping extends Command {

    @Override
    public void accept(Interaction t) {
        for (ChatCommand chatCommand : t.chatShellFactory.getChatCommands()) {
            if (chatCommand.getUserName().equalsIgnoreCase(t.parameters[0])) {
                chatCommand.onMessage(new Message("%s ti ha pingato\u0007".formatted(chatCommand.getUserName())));
            }
        }
    }

}
