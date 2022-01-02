package com.example.commands;

import com.example.Room;
import com.example.User;

public class Interaction {
    User user;
    Room room;
    String[] parameters;

    public Interaction(User user, Room room, String[] parameters) {
        this.user = user;
        this.room = room;
        this.parameters = parameters;
    }

}
