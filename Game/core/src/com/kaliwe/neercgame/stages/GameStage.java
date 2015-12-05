package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
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
import com.kaliwe.neercgame.screens.GameScreen;
import com.kaliwe.neercgame.screens.GameStateManager;
import com.kaliwe.neercgame.utils.*;

import java.util.Iterator;

/**
 * Created by anton on 18.11.15.
 */

public class GameStage extends Stage implements ContactListener {

    // This will be our viewport measurements while working with the debug renderer
    public static int VIEWPORT_WIDTH = 20;
    public static int VIEWPORT_HEIGHT = 13;

    public String text;

    protected short score;
    protected short maxScore;

    protected World world;
    public MapHolder mapHolder;
    protected Player player;

    protected final float TIME_STEP = 1 / 300f;
    protected float accumulator = 0f;

    protected int[] renderOnBg = { 0, 1, 2, 3 };
    protected int[] renderOnFg = { 4 };

    protected TiledMapRenderer tiledMapRenderer;

    protected Box2DDebugRenderer renderer;
    protected boolean lock = false;
    protected float cameraLowerY = 5f;
    protected OrthographicCamera hudCam;

    protected static float acc;
    protected static Sound sound;
    protected static long soundId;

    static {
        sound = ResourceUtils.getSound("main");
        soundId = sound.loop();
        sound.setVolume(soundId, 0.3f);
        sound.pause(soundId);

    }

    public GameStage(String mapName, int PPM, String text) {
        acc = 0;
        Constants.PPM = PPM;
        GameScreen.lock = false;
        this.text = text;

        score = 0;
        maxScore = 1;

        setupWorld(mapName);
        setupPlayer();
        setupCamera();

        setKeyboardFocus(null);
        world.setContactListener(this);
        renderer = new Box2DDebugRenderer();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(mapHolder.map, 1f / Constants.PPM);

        sound.resume(soundId);
    }

    protected void setupCamera() {
        getCamera().viewportHeight = VIEWPORT_HEIGHT;
        getCamera().viewportWidth = VIEWPORT_WIDTH;
        getCamera().position.set(player.getPosition(), 0f);
        getCamera().update();

        hudCam = new OrthographicCamera(VIEWPORT_WIDTH * 20, VIEWPORT_HEIGHT * 20);
    }

    protected void setupWorld(String mapName) {
        world = WorldUtils.createWorld();
        setupMap(mapName);
        WorldUtils.createFinish(world, mapHolder.map);
    }

    protected void setupPlayer() {
        player = new Player(WorldUtils.createPlayer(world, this.mapHolder.map, mapHolder));
        addActor(player);
    }

    protected void setupMap(String mapName) {
        mapHolder = WorldUtils.createMap(world, mapName);
        mapHolder.ground.forEach(this::addActor);
    }

    protected void updateCamera() {
        Vector3 camPos = new Vector3(
                Math.max(player.getPosition().x, mapHolder.spawn.x + VIEWPORT_WIDTH / 4),
                Math.max(cameraLowerY, player.getPosition().y),
                0f);
        getCamera().position.lerp(camPos, 0.11f);

        getCamera().update();
        tiledMapRenderer.setView((OrthographicCamera) getCamera());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        accumulator += delta;

        acc += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        if (player.getPosition().y < -50) {
            if (!lock) {
                lock = true;
                GameStateManager.failed(score, maxScore);
            }
        }

        updateCamera();

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
        tiledMapRenderer.render(renderOnBg);
        super.draw();
        tiledMapRenderer.render(renderOnFg);
        //renderer.render(world, getCamera().combined);
    }

    @Override
    public boolean keyDown(int keyCode) {
        switch (keyCode) {
            case Keys.UP:
            case Keys.SPACE:
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
            if (!lock) {
                lock = true;
                sound.pause(soundId);
                GameStateManager.complete(score, maxScore);
            }
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

    public static float getTime() {
        return acc;
    }

    public short getMaxScore() {
        return maxScore;
    }
}
