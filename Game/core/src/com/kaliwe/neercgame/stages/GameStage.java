package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kaliwe.neercgame.actors.Player;
import com.kaliwe.neercgame.utils.MapHolder;
import com.kaliwe.neercgame.utils.WorldUtils;

/**
 * Created by anton on 18.11.15.
 */

public class GameStage extends Stage {

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
        renderer = new Box2DDebugRenderer();
    }

    private void setupCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH,
                                        VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
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

        //TODO: Implement interpolation

    }

    @Override
    public void draw() {
        super.draw();
        renderer.render(world, camera.combined);
    }

}
