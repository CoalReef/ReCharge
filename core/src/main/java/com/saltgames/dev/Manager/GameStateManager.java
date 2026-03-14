package com.saltgames.dev.Manager;

import com.badlogic.gdx.Gdx;
import com.saltgames.dev.Main;
import com.saltgames.dev.States.PlayState;
import com.saltgames.dev.States.StartMenu;
import com.saltgames.dev.States.State;

import java.util.Stack;

public class GameStateManager {
    Main main;
    Stack<State> states;

    public GameStateManager(Main main) {
        states = new Stack<State>();
        states.push(new StartMenu(this));
        this.main = main;
    }

    public void setState(State stateToSet) {
        states.pop().dispose();
        states.push(stateToSet);
    }

    public void update() {
        states.peek().update(Gdx.graphics.getDeltaTime());
    }

    public void render() {
        states.peek().render();
    }

    public void dispose() {
        states.peek().dispose();
    }

    public Main getMain() {
        return main;
    }
}
