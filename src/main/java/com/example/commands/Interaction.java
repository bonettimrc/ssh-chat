package com.example.commands;

import com.example.ChatCommand;
import com.example.ChatShellFactory;

public class Interaction {
    ChatCommand chatCommand;
    ChatShellFactory chatShellFactory;
    String[] parameters;

    public Interaction(ChatCommand chatCommand, String[] parameters, ChatShellFactory chatShellFactory) {
        this.chatCommand = chatCommand;
        this.parameters = parameters;
        this.chatShellFactory = chatShellFactory;
    }

}
