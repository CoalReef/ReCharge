package com.saltgames.dev.ContactListener;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.saltgames.dev.GameObjects.DeathHitbox;
import com.saltgames.dev.GameObjects.Platform;
import com.saltgames.dev.GameObjects.Player;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

    private boolean isGrounded = false;
    private boolean isOnDamageBox = false;

    @Override
    public void beginContact(Contact contact) {
        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();

        if (objectA instanceof Player && objectB instanceof Platform) {
            isGrounded = true;
        } else if (objectA instanceof Player && objectB instanceof DeathHitbox) {
            isOnDamageBox = true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();

        if (objectA instanceof Player && objectB instanceof Platform) {
            isGrounded = false;
        } else if (objectA instanceof Player && objectB instanceof DeathHitbox) {
            isOnDamageBox = false;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    public boolean isGrounded() {
        if (isGrounded) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOnDamageBox() {
        if (isOnDamageBox) {
            return true;
        } else {
            return false;
        }
    }
}
