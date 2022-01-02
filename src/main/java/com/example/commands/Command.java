package com.example.commands;

import java.util.function.Consumer;

public abstract class Command implements Consumer<Interaction> {
    public abstract String getName();

    public abstract String getDescription();

    @Override
    public abstract void accept(Interaction t);
}
