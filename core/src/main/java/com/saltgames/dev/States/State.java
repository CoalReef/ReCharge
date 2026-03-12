package com.saltgames.dev.States;

public abstract class State {
    public State() {

    }

    public abstract void update(float dt); // dt = DeltaTime, keeps game running the same across different framerates

    public abstract void render();

    public abstract void dispose();
}
