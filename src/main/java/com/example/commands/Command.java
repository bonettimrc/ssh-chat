package com.example.commands;

import java.util.function.Consumer;

public abstract class Command implements Consumer<Interaction> {

    @Override
    public abstract void accept(Interaction t);

    public String getName() {
        return this.getClass().getSimpleName();
    }
}
