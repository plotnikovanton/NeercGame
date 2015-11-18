package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    public GameStage() {
        setupWorld();
        setupCamera();
        setKeyboardFocus(null);
        world.setContactListener(this);
        renderer = new Box2DDebugRenderer();
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

        camera.position.set(player.getPosition() ,0f);
        camera.update();
        //TODO: Implement interpolation

    }

    @Override
    public void draw() {
        super.draw();
        renderer.render(world, camera.combined);
    }

    @Override
    public boolean keyDown(int keyCode) {
        System.out.println("Key pressed " + keyCode);
        if (keyCode == Keys.UP) {
            player.jump();
        }
        if (keyCode == Keys.DOWN) {
            player.dodge();
        }
        if (keyCode == Keys.RIGHT) {
            player.addSpeed(Constants.PLAYER_SPEED);
        }
        if (keyCode == Keys.LEFT) {
            player.addSpeed(-Constants.PLAYER_SPEED);
        }

        return super.keyDown(keyCode);
    }

    @Override
    public boolean keyUp(int keyCode) {
        if (keyCode == Keys.DOWN) {
            player.stopDodge();
        }
        if (keyCode == Keys.LEFT) {
            player.addSpeed(Constants.PLAYER_SPEED);
        }
        if (keyCode == Keys.RIGHT) {
            player.addSpeed(-Constants.PLAYER_SPEED);
        }

        return true;
    }

    @Override
    public void beginContact(Contact contact) {
        System.out.println("Contact");
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if ((BodyUtils.bodyIsGround(a) && BodyUtils.bodyIsPlayer(b)) ||
                (BodyUtils.bodyIsPlayer(a) && BodyUtils.bodyIsGround(b))) {
            player.landed();
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
