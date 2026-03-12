package com.saltgames.dev.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.saltgames.dev.ContactListener.ContactListener;
import com.saltgames.dev.Manager.GameStateManager;
import com.saltgames.dev.States.GameOver;

import java.util.ArrayList;

public class Player {
    // Constructor defined variables
    float xPos;
    float yPos;
    int speed;
    OrthographicCamera cam;
    World world;
    ContactListener contactListener;
    GameStateManager gsm;

    // Created within player object
    ShapeRenderer shape;
    BodyDef bodyDef;
    Body body;
    FixtureDef fixtureDef;
    Fixture fixture;
    PolygonShape hitboxShape;
    int jumpCounter = 0;
    Bullet bullet;
    int direction = 1; // Player will always start by looking right (0 = Left, 1 = Right)
    boolean bulletExists = false; // Track if bullet exists for rendering and update purposes
    ArrayList<Bullet> bulletsArray;
    Bullet hit;

    public Player (float xPos, float yPos, int speed, OrthographicCamera cam, World world, ContactListener contactListener, GameStateManager gsm) {

        // Store given variables
        this.xPos = xPos;
        this.yPos = yPos;
        this.speed = speed;
        this.cam = cam;
        this.world = world;
        this.gsm = gsm;

        // Create the shape renderer for the player rect (temp)
        shape = new ShapeRenderer();

        // Contact listener
        this.contactListener = contactListener;

        // Create the player Box2D body
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPos, yPos);
        body = world.createBody(bodyDef);
        body.setUserData(this);

        // Create the player Box2D Fixture
        fixtureDef = new FixtureDef();
        hitboxShape = new PolygonShape();
        hitboxShape.setAsBox(0.5f, 0.5f);
        fixtureDef.shape = hitboxShape;
        fixture = body.createFixture(fixtureDef);

        // Initialize Bullet Array
        bulletsArray = new ArrayList<Bullet>();

    }

    // User input logic
    public void handleInput() {

        final float MAX_SPEED = 5f; // Players max X velocity for movement

        // Left and right movement with A and D
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.setLinearVelocity(5f, body.getLinearVelocity().y);
            direction = 1;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.setLinearVelocity(-5f, body.getLinearVelocity().y);
            direction = 0;
        }
        else {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }

        // Jumping
        final int MAX_JUMPS = 1; // ACTUALLY is this +1

        // Check if the player has been grounded yet and reset the jump count if so
        if (contactListener.isGrounded()) {
            jumpCounter = 0;
        }

        // Allow player to jump until max jumps reached
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && jumpCounter < MAX_JUMPS) {
            body.applyLinearImpulse(0, 5f, 0, 0, true);
            jumpCounter++;
        }

        // Shoot bullet
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            bullet = new Bullet(gsm, direction, shape, world, this);
            bulletExists = true;
            bulletsArray.add(bullet);
        }


        // Check if the bullet hit a wall and should be deleted
        if (contactListener.BulletHitWall()) {
            hit = contactListener.bulletToDelete();

            // Get rid of the hitbox
            if (!world.isLocked()) {
                world.destroyBody(hit.body);
                if (bulletsArray.isEmpty()) {
                    bulletExists = false;
                }
            }
            bulletsArray.remove(hit);

            // Check if there ARE bullets to stop rendering and updating if none exist
            if (bulletsArray.isEmpty()) {
                bulletExists = false;
            }
        }
    }


    public void update() {
        if (contactListener.isOnDamageBox()) {
            gsm.setState(new GameOver(gsm, gsm.getMain().getBatch()));
        }

        // Only call bullets update if it exists
        if (bulletExists) {
            for (int i = 0; i < bulletsArray.size(); i++) {
                bulletsArray.get(i).update();
            }
        }
    }

    public void render() {
        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(body.getPosition().x - 0.5f, body.getPosition().y - 0.5f, 1f, 1f); // Body positions make sprite follow hitbox
        shape.end();

        // Only render the bullet if it even exists
        if (bulletExists) {
            for (int i = 0; i < bulletsArray.size(); i++) {
                bulletsArray.get(i).render();
            }
        }
    }

    public void dispose() {
        hitboxShape.dispose();
        shape.dispose();

        if (bulletExists) {
            for (int i = 0; i < bulletsArray.size(); i++) {
                bulletsArray.get(i).dispose();
            }
        }
    }

    // Getters and setters for position
    public float getXPos() {
        return body.getPosition().x - 0.5f;
    }

    public float getYPos() {
       return body.getPosition().y - 0.5f;
    }
}
