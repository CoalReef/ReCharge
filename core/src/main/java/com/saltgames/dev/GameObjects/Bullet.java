package com.saltgames.dev.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.saltgames.dev.Manager.GameStateManager;

public class Bullet {

    // Given Variables from constructor
    GameStateManager gsm;
    int direction;
    ShapeRenderer shape;
    World world;
    Player player;

    // Class specific variables
    int xOffset;
    int xVelocity;
    BodyDef bodyDef;
    Body body;
    FixtureDef fixtureDef;
    Fixture fixture;

    public Bullet(GameStateManager gsm, int direction, ShapeRenderer shape, World world, Player player) {
        this.gsm = gsm;
        this.direction = direction; // (0 = Left, 1 = Right)
        this.shape = shape;
        this.world = world;
        this.player = player;

        // Create the bullets body & fixture
        handleDirection(); // Need player direction for bullet creation
        bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(player.body.getPosition().x + xOffset, player.body.getPosition().y));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.gravityScale = 0f; // Keep bullet unaffected by gravity
        body = world.createBody(bodyDef);

        fixtureDef = new FixtureDef();
        PolygonShape bulletShape = new PolygonShape();
        bulletShape.setAsBox(1f, 1f);
        fixtureDef.shape = bulletShape;
        fixture = body.createFixture(fixtureDef);
    }

    // Figure out the offset for the bullet spawn based on player direction their facing
    public void handleDirection() {
        if (direction == 0) { // (0 = Left, 1 = Right)
            xOffset = -5;
            xVelocity = -100;
        } else if (direction == 1) {
            xOffset = 5;
            xVelocity = 100;
        }
    }

    public void update() {
        handleDirection();
        body.applyLinearImpulse(xVelocity, 0, body.getPosition().x, body.getPosition().y, true);
    }

    public void render() {
        handleDirection();
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(xOffset, player.body.getPosition().y, 1, 1);
        shape.end();
    }

    public void dispose() {
        shape.dispose();
    }
}
