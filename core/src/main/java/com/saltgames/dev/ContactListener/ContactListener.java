package com.saltgames.dev.ContactListener;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.saltgames.dev.GameObjects.Bullet;
import com.saltgames.dev.GameObjects.DeathHitbox;
import com.saltgames.dev.GameObjects.Platform;
import com.saltgames.dev.GameObjects.Player;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

    private boolean isGrounded = false;
    private boolean isOnDamageBox = false;
    private Bullet bulletToDelete;
    private boolean bulletHitWall;

    @Override
    public void beginContact(Contact contact) {
        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();

        if (objectA instanceof Player && objectB instanceof Platform) {
            isGrounded = true;
        } else if (objectA instanceof Player && objectB instanceof DeathHitbox) {
            isOnDamageBox = true;

            // Return the bullet that needs to be deleted
        } else if (objectA instanceof Platform && objectB instanceof Bullet || objectA instanceof Bullet && objectB instanceof Platform) {
            bulletHitWall = true;
            if (objectA instanceof Platform && objectB instanceof Bullet) {
                bulletToDelete = (Bullet) objectB;
            } else if ( objectA instanceof Bullet && objectB instanceof Platform) {
                bulletToDelete = (Bullet) objectA;
            }
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
        } else if (objectA instanceof Platform && objectB instanceof Bullet || objectA instanceof Bullet && objectB instanceof Platform) {
            bulletHitWall = false;
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

    public boolean BulletHitWall() {
        if (bulletHitWall) {
            return true;
        } else {
            return false;
        }
    }

    public Bullet bulletToDelete() {
        return bulletToDelete;
    }
}
