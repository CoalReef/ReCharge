package com.saltgames.dev.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
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

    public PlayState(GameStateManager gsm) {

        // Set the GSM to be usable
        this.gsm = gsm;

        // Set up the map variables & camera
        map = new TmxMapLoader().load("Polished Test Map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/16f); // 1 World tile = 16 Pixels
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 16, 9);

        // Create the Box2D World
        world = new World(new Vector2(0, -0.08f), true);

        // Create the player object
        player = new Player(8, 4.5f, 1, cam, world);

        // Create hitboxes for the map
        mapBodyDef = new BodyDef();
        mapBodyDef.type = BodyDef.BodyType.StaticBody;

    }

    public void update(float dt) {
        player.handleInput();
        world.step(dt, 6, 2);
    }

    public void render() {
        mapRenderer.setView(cam);
        mapRenderer.render();
        player.render();
    }

    public void dispose() {
        player.dispose();
    }
}
