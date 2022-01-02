package com.example.commands;

import com.example.Message;

public class Help extends Command {

    @Override
    public void accept(Interaction t) {
        StringBuilder stringBuilder = new StringBuilder("\r\n");
        for (Command command : t.room.getCommands()) {
            stringBuilder.append("%s:%s\r\n".formatted(command.getName(), command.getDescription()));
        }
        t.user.onMessageRecieved(new Message(stringBuilder.toString()));
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "provide help";
    }

}
