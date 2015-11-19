package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kaliwe.neercgame.actors.Player;
import com.kaliwe.neercgame.utils.BodyUtils;
import com.kaliwe.neercgame.utils.Constants;
import com.kaliwe.neercgame.utils.MapHolder;
import com.kaliwe.neercgame.utils.WorldUtils;

/**
 * Created by anton on 18.11.15.
 */

public class GameStage extends Stage implements ContactListener {

    // This will be our viewport measurements while working with the debug renderer
    private static final int VIEWPORT_WIDTH = 20;
    private static final int VIEWPORT_HEIGHT = 13;

    private World world;
    private MapHolder mapHolder;
    private Player player;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private TiledMapRenderer tiledMapRenderer;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    public GameStage() {
        setupWorld();
        setupCamera();
        setKeyboardFocus(null);
        world.setContactListener(this);
        renderer = new Box2DDebugRenderer();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(mapHolder.map, 0.1f);
    }

    private void setupCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH,
                                        VIEWPORT_HEIGHT);
        //camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.position.set(player.getPosition() ,0f);
        camera.update();
    }

    public void setupWorld() {
        world = WorldUtils.createWorld();
        setupMap();
        setupPlayer();
    }

    private void setupPlayer() {
        player = new Player(WorldUtils.createPlayer(world));
        addActor(player);
    }

    private void setupMap() {
        mapHolder = WorldUtils.createMap(world, "level1.tmx");
        mapHolder.ground.forEach(this::addActor);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Fixed timestep
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        player.update();

        camera.position.set(player.getPosition() ,0f);
        camera.update();
        //TODO: Implement interpolation

    }

    @Override
    public void draw() {
        super.draw();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        renderer.render(world, camera.combined);
    }

    @Override
    public boolean keyDown(int keyCode) {
        System.out.println("Key pressed " + keyCode);
        switch (keyCode) {
            case Keys.UP:
                player.jump();
                break;
            case Keys.DOWN:
                player.dodge();
                break;
            case Keys.LEFT:
                player.addSpeed(-Constants.PLAYER_SPEED);
                break;
            case Keys.RIGHT:
                player.addSpeed(Constants.PLAYER_SPEED);
                break;
        }
        return super.keyDown(keyCode);
    }

    @Override
    public boolean keyUp(int keyCode) {
        switch (keyCode) {
            case Keys.DOWN:
                player.stopDodge();
                break;
            case Keys.LEFT:
                player.addSpeed(Constants.PLAYER_SPEED);
                break;
            case Keys.RIGHT:
                player.addSpeed(-Constants.PLAYER_SPEED);
                break;
        }
        return true;
    }

    @Override
    public void beginContact(Contact contact) {
        System.out.println("Contact");
        Fixture af = contact.getFixtureA();
        Body a = af.getBody();
        Fixture bf = contact.getFixtureB();
        Body b = bf.getBody();


        if ((BodyUtils.bodyIsGround(a) && BodyUtils.fixtureIsFoot(bf)) ||
                (BodyUtils.fixtureIsFoot(af) && BodyUtils.bodyIsGround(b))) {
            player.landed();
        }
    }

    @Override
    public void endContact(Contact contact) {
        System.out.print("End Contact");
        Fixture af = contact.getFixtureA();
        Body a = af.getBody();
        Fixture bf = contact.getFixtureB();
        Body b = bf.getBody();


        if ((BodyUtils.bodyIsGround(a) && BodyUtils.fixtureIsFoot(bf)) ||
                (BodyUtils.fixtureIsFoot(af) && BodyUtils.bodyIsGround(b))) {
            player.jumped();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
