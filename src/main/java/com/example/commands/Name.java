package com.example.commands;

public class Name extends Command {

    @Override
    public String getName() {
        return "name";
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void accept(Interaction t) {
        t.user.setUserName(t.parameters[0]);

    }

}
