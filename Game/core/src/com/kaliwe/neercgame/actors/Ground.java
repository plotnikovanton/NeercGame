package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.kaliwe.neercgame.box2d.GroundUserData;

/**
 * Created by anton on 18.11.15.
 */
public class Ground extends GameActor {
    public Ground(Body body) {
        super(body);
    }

    @Override
    public GroundUserData getUserData() {
        return (GroundUserData) userData;
    }
}
