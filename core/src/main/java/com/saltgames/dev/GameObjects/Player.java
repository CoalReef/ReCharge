package com.saltgames.dev.GameObjects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player {
    // Constructor defined variables
    float xPos;
    float yPos;
    int speed;
    OrthographicCamera cam;

    // Created within player object
    ShapeRenderer shape;

    public Player (float xPos, float yPos, int speed, OrthographicCamera cam) {

        // Set the players starting coords and default speed and pass Player object the camera
        this.xPos = xPos;
        this.yPos = yPos;
        this.speed = speed;
        this.cam = cam;

        // Create the shape renderer for the player rect (temp)
        shape = new ShapeRenderer();
    }

    public void update() {
    }

    public void render() {
        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(xPos, yPos, 1, 1);
        shape.end();
    }

    public void dispose() {
        shape.dispose();
    }
}
