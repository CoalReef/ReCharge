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
    int bulletArrayIndex; // Keep track of where to reference in the array when the bullet is deleted

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
        body.setUserData(this); // Assign bullet as body type for collision detection

        fixtureDef = new FixtureDef();
        PolygonShape bulletShape = new PolygonShape();
        bulletShape.setAsBox(0.15f, 0.15f);
        fixtureDef.shape = bulletShape;
        fixture = body.createFixture(fixtureDef);

        // Done on creation so the impulse is only applied once
        body.applyLinearImpulse(xVelocity, 0, body.getPosition().x, body.getPosition().y, true);
    }

    // Figure out the offset for the bullet spawn based on player direction their facing
    public void handleDirection() {
        if (direction == 0) { // (0 = Left, 1 = Right)
            xOffset = -1;
            xVelocity = -50;
        } else if (direction == 1) {
            xOffset = 1;
            xVelocity = 50;
        }
    }

    public void update() {
        handleDirection();
    }

    public void render() {
        handleDirection();
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(body.getPosition().x - 0.15f, body.getPosition().y - 0.15f, 0.3f, 0.3f); // Hardcoded the 0.15f because the math was too much and idc enough icl (hi future me when this causes a bug later)
        shape.end();
    }

    public void dispose() {
        shape.dispose();
    }
}
