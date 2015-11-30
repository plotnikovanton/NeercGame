package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kaliwe.neercgame.actors.DisappearObject;
import com.kaliwe.neercgame.actors.Enemy;
import com.kaliwe.neercgame.actors.Player;
import com.kaliwe.neercgame.enums.PlayerState;
import com.kaliwe.neercgame.utils.Constants;
import com.kaliwe.neercgame.utils.ContactUtils;
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
        tiledMapRenderer = new OrthogonalTiledMapRenderer(mapHolder.map, 1f / Constants.PPM);
    }

    private void setupCamera() {
        getCamera().viewportHeight = VIEWPORT_HEIGHT;
        getCamera().viewportWidth = VIEWPORT_WIDTH;
        getCamera().position.set(player.getPosition(), 0f);
        getCamera().update();
    }

    public void setupWorld() {
        world = WorldUtils.createWorld();
        setupMap();
        setupPlayer();
        setupEnemies();
    }

    private void setupPlayer() {
        player = new Player(WorldUtils.createPlayer(world, mapHolder.map));
        addActor(player);
    }

    private void setupEnemies() {
        for (Body b : WorldUtils.createSimpleEnemy(world, mapHolder.map)) {
            addActor(new Enemy(b));
        }

        for (Body b : WorldUtils.createDisappearObjects(world, mapHolder.map)) {
            System.out.println("Got one");
            addActor(new DisappearObject(b));
        }
    }

    private void setupMap() {
        mapHolder = WorldUtils.createMap(world, "level0.tmx");
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

        Vector3 camPos = new Vector3(player.getPosition().x, player.getPosition().y ,0f);
        getCamera().position.lerp(camPos, 0.1f);

        getCamera().update();
    }

    @Override
    public void draw() {
        super.draw();
        tiledMapRenderer.setView((OrthographicCamera) getCamera());
        tiledMapRenderer.render();
        renderer.render(world, getCamera().combined);
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
}
