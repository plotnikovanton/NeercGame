package com.kaliwe.neercgame.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by anton on 18.11.15.
 */
public class WorldUtils {
    public static World createWorld() {
        return new World(Constants.WORLD_GRAVITY, true);
    }

    public static Body createGround(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(10, 10));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(100, 10);
        body.createFixture(shape, 1);
        shape.dispose();
        return body;
    }
}
