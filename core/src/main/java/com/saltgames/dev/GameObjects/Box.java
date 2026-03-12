package com.saltgames.dev.GameObjects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.saltgames.dev.ContactListener.ContactListener;
import com.saltgames.dev.Manager.GameStateManager;

public class Box {

    // Given variables to set during construction
    GameStateManager gsm;
    ContactListener contactListener;
    World world;
    ShapeRenderer shape;
    int xSpawn;
    int ySpawn;
    int height;
    int width;

    // Variables unique to box class
    BodyDef bodyDef;
    Body body;
    FixtureDef fixtureDef;
    Fixture fixture;


    public Box (GameStateManager gsm, ContactListener contactListener, World world, ShapeRenderer shape, int xSpawn, int ySpawn, int height, int width) {
        this.gsm = gsm;
        this.contactListener = contactListener;
        this.world = world;
        this.shape = shape;
        this.xSpawn = xSpawn; // Needs provided spawn coords for different spawns for different levels
        this.ySpawn = ySpawn;
        this.height = height;
        this.width = width;

        // Create the box collision
        bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(xSpawn, ySpawn));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        body.setUserData(this);

        fixtureDef = new FixtureDef();
        PolygonShape boxColliderShape = new PolygonShape();
        boxColliderShape.setAsBox(width / 2f, height / 2f);
        fixtureDef.shape = boxColliderShape;
        body.createFixture(fixtureDef);

        // Change the mass data for the body for better interaction with the bullets
        MassData md = new MassData();
        md.mass = 10f;
        md.center.set(0, 0);
        md.I = 1.0f;
        body.setMassData(md);
    }

    public void update() {

    }

    public void render() {
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.rect(body.getPosition().x - (width / 2f), body.getPosition().y - (height / 2f), width, height);
        shape.end();

    }

    public void dispose() {
        shape.dispose();
    }
}
