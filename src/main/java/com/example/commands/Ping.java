package com.example.commands;

import com.example.Message;
import com.example.User;

public class Ping extends Command {
    @Override
    public void accept(Interaction t) {
        for (User user : t.room.getUsers()) {
            if (user.getUserName().equalsIgnoreCase(t.parameters[0])) {
                user.onMessageRecieved(new Message(String.format("%s ti ha pingato\u0007", t.user.getUserName())));
            }
        }
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "ping user";
    }

}
