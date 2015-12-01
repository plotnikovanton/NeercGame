package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.kaliwe.neercgame.actors.Player;
import com.kaliwe.neercgame.box2d.UserData;
import com.kaliwe.neercgame.enums.PlayerState;
import com.kaliwe.neercgame.utils.Constants;
import com.kaliwe.neercgame.utils.ContactUtils;
import com.kaliwe.neercgame.utils.MapHolder;
import com.kaliwe.neercgame.utils.WorldUtils;

import java.util.Iterator;

/**
 * Created by anton on 18.11.15.
 */

public class GameStage extends Stage implements ContactListener {

    // This will be our viewport measurements while working with the debug renderer
    protected static final int VIEWPORT_WIDTH = 20;
    protected static final int VIEWPORT_HEIGHT = 13;

    protected boolean next = false;

    protected World world;
    protected MapHolder mapHolder;
    protected Player player;

    protected final float TIME_STEP = 1 / 300f;
    protected float accumulator = 0f;

    protected TiledMapRenderer tiledMapRenderer;

    protected Box2DDebugRenderer renderer;
    protected boolean failed = false;

    public GameStage(String mapName) {
        setupWorld(mapName);
        setupPlayer();
        setupCamera();

        setKeyboardFocus(null);
        world.setContactListener(this);
        renderer = new Box2DDebugRenderer();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(mapHolder.map, 1f / Constants.PPM);
    }

    private void setupCamera() {
        getCamera().viewportHeight = VIEWPORT_HEIGHT;
        getCamera().viewportWidth = VIEWPORT_WIDTH;
        getCamera().position.set(player.getPosition(), 0f);
        getCamera().update();
    }

    public void setupWorld(String mapName) {
        world = WorldUtils.createWorld();
        setupMap(mapName);
        WorldUtils.createFinish(world, mapHolder.map);
    }

    private void setupPlayer() {
        player = new Player(WorldUtils.createPlayer(world, mapHolder.map));
        addActor(player);
    }

    private void setupMap(String mapName) {
        mapHolder = WorldUtils.createMap(world, mapName);
        mapHolder.ground.forEach(this::addActor);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        if (player.getPosition().y < -50) {
            failed = true;
        }

        Vector3 camPos = new Vector3(player.getPosition().x, player.getPosition().y - 3 , 0f);
        getCamera().position.lerp(camPos, 0.1f);

        getCamera().update();
        Array<Body> bodies = new Array();
        world.getBodies(bodies);
        for (Iterator<Body> i = bodies.iterator(); i.hasNext(); ) {
            Body body = i.next();
            Object rawData = body.getUserData();
            if (rawData != null) {
                if (rawData instanceof UserData) {
                    if (((UserData) rawData).isFlaggedForDelete) {
                        world.destroyBody(body);
                        body.setUserData(null);
                    }
                }
            }
        }
    }

    @Override
    public void draw() {
        tiledMapRenderer.setView((OrthographicCamera) getCamera());
        tiledMapRenderer.render();
        renderer.render(world, getCamera().combined);
        super.draw();
    }

    @Override
    public boolean keyDown(int keyCode) {
        switch (keyCode) {
            case Keys.UP:
                player.jump();
                break;
            case Keys.LEFT:
                player.setTurnRight(false);
                player.setState(PlayerState.WALK);
                break;
            case Keys.RIGHT:
                player.setTurnRight(true);
                player.setState(PlayerState.WALK);
                break;
        }
        return super.keyDown(keyCode);
    }

    @Override
    public boolean keyUp(int keyCode) {
        switch (keyCode) {
            case Keys.LEFT:
            case Keys.RIGHT:
                player.setState(PlayerState.STAND);
                break;
        }
        return true;
    }

    @Override
    public void beginContact(Contact contact) {
        if (ContactUtils.checkFixtureAndBody(
                ContactUtils.isFixtureFoot, ContactUtils.isBodyGround, contact)) {
            player.incNumOfFootContacts();
        } else if (ContactUtils.checkFixtureAndBody(
                ContactUtils.isFixtureFoot, ContactUtils.isBodySimpleEnemy, contact)) {
            player.jumpOutOfEnemy();
        } else if (ContactUtils.checkFixtureAndBody(
                ContactUtils.isFixtureFinish, ContactUtils.isBodyPlayer, contact)) {
            next = true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        if (ContactUtils.checkFixtureAndBody(
                ContactUtils.isFixtureFoot, ContactUtils.isBodyGround, contact)) {
            player.decNumOfFootContacts();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isFailed() {
        return failed;
    }

    public boolean isNext() {
        return next;
    }
}
