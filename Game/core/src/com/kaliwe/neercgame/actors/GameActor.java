package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by anton on 18.11.15.
 */
public class GameActor extends Actor {
    protected Body body;

    public GameActor(Body body) {
        this.body = body;
    }
}
