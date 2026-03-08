package com.saltgames.dev.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.saltgames.dev.Manager.GameStateManager;

public class GameOver extends State{
    GameStateManager gsm;
    SpriteBatch batch;
    BitmapFont font;
    public GameOver(GameStateManager gsm, SpriteBatch batch) {
        this.gsm = gsm;
        this.batch = batch;
        font = new BitmapFont();
    }

    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gsm.setState(new PlayState(gsm));
        }
    }

    public void render() {
        batch.begin();
        font.draw(batch, "Press Space to Restart", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        batch.end();
    }

    public void dispose() {
        font.dispose();
    }
}
