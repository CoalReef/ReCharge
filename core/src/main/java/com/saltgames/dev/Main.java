package com.saltgames.dev;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.saltgames.dev.Manager.GameStateManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private GameStateManager gsm;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gsm = new GameStateManager(this);

    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        gsm.update();
        gsm.render();
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        gsm.dispose();
    }
}
