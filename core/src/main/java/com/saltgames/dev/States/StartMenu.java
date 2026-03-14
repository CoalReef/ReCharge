package com.saltgames.dev.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.saltgames.dev.Manager.GameStateManager;

public class StartMenu extends State {
    GameStateManager gsm;
    BitmapFont font;
    SpriteBatch batch;

    public StartMenu(GameStateManager gsm) {
        this.gsm = gsm;
        font = new BitmapFont();
    }

    public void update(float dt) {
        // Check if space was pressed to proceed to play state
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gsm.setState(new PlayState(gsm));
        }
    }

    public void render() {
        gsm.getMain().getBatch().begin();
        font.draw(gsm.getMain().getBatch(), "Press Space to Start Game", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        gsm.getMain().getBatch().end();
    }

    public void dispose() {
        font.dispose();
    }

}
