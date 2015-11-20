package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.kaliwe.neercgame.box2d.SimpleEnemyUserData;

/**
 * Created by anton on 20.11.15.
 */
public class Enemy extends GameActor {
    public Enemy(Body body) {
        super(body);
    }

    @Override
    public SimpleEnemyUserData getUserData() {
        return (SimpleEnemyUserData) body.getUserData();
    }
}
