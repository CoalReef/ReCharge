package com.saltgames.dev.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.saltgames.dev.ContactListener.ContactListener;
import com.saltgames.dev.GameObjects.DeathHitbox;
import com.saltgames.dev.GameObjects.Platform;
import com.saltgames.dev.GameObjects.Player;
import com.saltgames.dev.Manager.GameStateManager;


public class PlayState extends State{
    GameStateManager gsm;

    // Variables for the map
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    OrthographicCamera cam;
    Player player;
    World world;
    BodyDef mapBodyDef;
    Body mapBody;
    MapLayer collisionLayer;
    MapObjects collisionLayerObjects;
    Box2DDebugRenderer debugRenderer;
    ContactListener contactListener;

    // Game Objects creation for marking hitbox types
    DeathHitbox deathHitbox = new DeathHitbox();
    Platform platform = new Platform();

    public PlayState(GameStateManager gsm) {
        this.gsm = gsm;

        map = new TmxMapLoader().load("industrial-box-map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/32f); // 1 World tile = 32 Pixels
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 32, 18);


        // World for Box2D Physics
        world = new World(new Vector2(0, -9.8f), true);

        // Collision listen set for world, used to check what type of platform player is one (death, bounce, floor)
        contactListener = new ContactListener();
        world.setContactListener(contactListener);

        player = new Player(1f, 15f, 1, cam, world, contactListener, gsm); // Needs to be AFTER world, its being passed it

        // REMOVE BEFORE RELEASE** Used to see hitboxes
        debugRenderer = new Box2DDebugRenderer();

        createHitboxes("PlatformCollisions", platform, world);
        createHitboxes("DamageCollisions", deathHitbox, world);

    }

    public void update(float dt) {
        player.update();
        cam.position.x = player.getXPos();
        cam.position.y = player.getYPos();
        world.step(dt, 6, 2);
        player.handleInput();
    }

    public void render() {
        cam.update();
        mapRenderer.setView(cam);
        mapRenderer.render();
        player.render();
        debugRenderer.render(world, cam.combined);
    }

    public void dispose() {
        player.dispose();
    }

    public void createHitboxes(String layerName, Object layerType, World world) {
        // Get the platform collision layer and objects from layer
        collisionLayer = map.getLayers().get(layerName);
        collisionLayerObjects = collisionLayer.getObjects();

        // Give all the collision objects under the platform layer a platform hitbox
        for (int i = 0; i < collisionLayerObjects.getCount(); i++) {

            // Get specific object and its general properties
            MapObject mapObj = collisionLayerObjects.get(i);
            MapProperties mapProperties = mapObj.getProperties();

            // Store the objects properties individually and divide by worlds pixels per meter (32)
            float bodyXPos = mapProperties.get("x", Float.class) / 32f;
            float bodyYPos = mapProperties.get("y", Float.class) / 32f;
            float fixtureWidth = mapProperties.get("width", Float.class) / 32f;
            float fixtureHeight = mapProperties.get("height", Float.class) / 32f;

            // Find center of platform for body placment
            float oppositeX = bodyXPos + fixtureWidth;
            float oppositeY = bodyYPos + fixtureHeight;

            float centerXPos = (oppositeX + bodyXPos) / 2f;
            float centerYPos = (oppositeY + bodyYPos) / 2f;

            // Create the body
            BodyDef platformBodyDef = new BodyDef();
            platformBodyDef.type = BodyDef.BodyType.StaticBody;
            platformBodyDef.position.x = centerXPos;
            platformBodyDef.position.y = centerYPos;
            Body platformBody = world.createBody(platformBodyDef);
            platformBody.setUserData(layerType); // Mark as a platform

            // Shape of fixture, properties of object used to determine exact shape
            PolygonShape platformShape = new PolygonShape();
            platformShape.setAsBox(fixtureWidth / 2, fixtureHeight / 2);

            // Create Fixture
            FixtureDef platformFixtureDef = new FixtureDef();
            platformFixtureDef.shape = platformShape;
            Fixture platformFixture = platformBody.createFixture(platformFixtureDef);
        }
    }
}
