package com.saltgames.dev.States;

public abstract class State {
    public State() {

    }

    public abstract void update(float dt);

    public abstract void render();

    public abstract void dispose();
}
