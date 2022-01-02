package com.example;

import java.util.ArrayList;
import java.util.Arrays;

import com.example.commands.Command;
import com.example.commands.Help;
import com.example.commands.Interaction;
import com.example.commands.Name;
import com.example.commands.Ping;

public class Room {
    private ArrayList<User> users;
    private ArrayList<Command> commands;
    private final String COMMAND_PREFIX = "/";

    public Room() {
        this.users = new ArrayList<User>();
        this.commands = new ArrayList<Command>();
        // commands might get added by class instead of instance?
        // ~ not possible, would break anonymous commands
        // still need to find a way to remove redoundaces of command name
        this.commands.add(new Ping());
        this.commands.add(new Help());
        this.commands.add(new Name());
    }

    public void onMessage(Message message) {
        String content = message.getContent();
        if (content.startsWith(COMMAND_PREFIX)) {
            String[] strings = content.substring(1).split(" ");
            String[] parameters = Arrays.copyOfRange(strings, 1, strings.length);
            String commandName = strings[0];
            Interaction interaction = new Interaction(message.getSenderUser(), this, parameters);
            for (Command command : commands) {
                if (command.getName().equalsIgnoreCase(commandName)) {
                    command.accept(interaction);
                }
            }
            return;
        }
        for (User user : users) {
            user.onMessageRecieved(message);
        }
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

}
