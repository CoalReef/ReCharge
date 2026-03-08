package com.saltgames.dev.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

    public PlayState(GameStateManager gsm) {

        // Set the GSM to be usable
        this.gsm = gsm;

        // Set up the map variables & camera
        map = new TmxMapLoader().load("industrial-box-map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/32f); // 1 World tile = 16 Pixels
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 32, 18);

        // Create the Box2D World
        world = new World(new Vector2(0, -9.8f), true);

        // Set the collision listener for the world
        contactListener = new com.saltgames.dev.ContactListener.ContactListener();
        world.setContactListener(contactListener);

        // Create the player object
        player = new Player(1f, 15f, 1, cam, world, contactListener);

        // Debug Renderer
        debugRenderer = new Box2DDebugRenderer();

        // Get the layer and objects meant for collision
        collisionLayer = map.getLayers().get("PlatformCollisions");
        collisionLayerObjects = collisionLayer.getObjects();

        // Loop through the collision objects and give them all hitboxes
        for (int i = 0; i < collisionLayerObjects.getCount(); i++) {

            // Get map object for the object at (i), need for its properties
            MapObject mapObj = collisionLayerObjects.get(i);
            MapProperties mapProperties = mapObj.getProperties();

            // Store the properties as variables and perform calculations for accurate use
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
            platformBody.setUserData(new Platform());

            // Create shape for fxiture
            PolygonShape platformShape = new PolygonShape();
            platformShape.setAsBox(fixtureWidth / 2, fixtureHeight / 2);

            // Create Fixture
            FixtureDef platformFixtureDef = new FixtureDef();
            platformFixtureDef.shape = platformShape;
            Fixture platformFixture = platformBody.createFixture(platformFixtureDef);
        }

    }

    public void update(float dt) {
        player.handleInput();
        cam.position.x = player.getXPos();
        cam.position.y = player.getYPos();
        world.step(dt, 6, 2);
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
}
