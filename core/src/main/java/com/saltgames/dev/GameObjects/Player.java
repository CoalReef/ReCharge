package com.saltgames.dev.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player {
    // Constructor defined variables
    float xPos;
    float yPos;
    int speed;
    OrthographicCamera cam;
    World world;

    // Created within player object
    ShapeRenderer shape;
    BodyDef bodyDef;
    Body body;
    FixtureDef fixtureDef;
    Fixture fixture;
    PolygonShape hitboxShape;

    public Player (float xPos, float yPos, int speed, OrthographicCamera cam, World world) {

        // Set the players starting coords and default speed and pass Player object the camera and world for Box2D
        this.xPos = xPos;
        this.yPos = yPos;
        this.speed = speed;
        this.cam = cam;
        this.world = world;

        // Create the shape renderer for the player rect (temp)
        shape = new ShapeRenderer();

        // Create the player Box2D body
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPos, yPos);
        body = world.createBody(bodyDef);

        // Create the player Box2D Fixture
        fixtureDef = new FixtureDef();
        hitboxShape = new PolygonShape();
        hitboxShape.setAsBox(0.5f, 0.5f);
        fixtureDef.shape = hitboxShape;
        fixture = body.createFixture(fixtureDef);
    }

    // User input logic
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.setLinearVelocity(5, body.getLinearVelocity().y); // getLinearVelocity().y Keeps the current y velocity for the player
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.setLinearVelocity(-5, body.getLinearVelocity().y);
        }
        else {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }
    }

    public void update() {

    }

    public void render() {
        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(body.getPosition().x, body.getPosition().y, 0.5f, 0.5f); // Body positions make sprite follow hitbox
        shape.end();
    }

    public void dispose() {
        hitboxShape.dispose();
        shape.dispose();
    }
}
