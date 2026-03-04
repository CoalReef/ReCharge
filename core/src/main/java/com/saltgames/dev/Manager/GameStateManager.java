package com.saltgames.dev.Manager;

import com.saltgames.dev.Main;
import com.saltgames.dev.States.PlayState;
import com.saltgames.dev.States.State;

import java.util.Stack;

public class GameStateManager {
    Main main;
    Stack<State> states;

    public GameStateManager(Main main) {
        states = new Stack<State>();
        states.push(new PlayState(this));
        this.main = main;
    }

    public void SetState(State stateToSet) {
        states.pop().dispose();
        states.push(stateToSet);
    }

    public void update() {
        states.peek().update();
    }

    public void render() {
        states.peek().render();
    }

    public void dispose() {
        states.peek().dispose();
    }
}
