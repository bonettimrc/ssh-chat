package com.example.commands;

import java.util.function.Consumer;

public interface Command extends Consumer<Interaction> {

    @Override
    public void accept(Interaction t);

    public String getName();
}
