package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.kaliwe.neercgame.box2d.SimpleEnemyUserData;
import com.kaliwe.neercgame.utils.Constants;

/**
 * Created by anton on 20.11.15.
 */
public class Enemy extends GameActor {
    private Vector2 currentTask;
    private boolean moveFront;
    private int pointer;

    public Enemy(Body body) {
        super(body);
        moveFront = true;
        pointer = 0;
        setVelocity();
    }

    @Override
    public void act(float delta) {
        if (body.getLinearVelocity().x < 0.01) {
            reverseVelocity();
        } else {
            setVelocity();
        }
    }

    public void setVelocity() {
        if (pointer == 0) {
            body.setLinearVelocity(new Vector2(-Constants.SIMPLE_ENEMY_SPEED, 0));
        } else {
            body.setLinearVelocity(new Vector2(Constants.SIMPLE_ENEMY_SPEED, 0));
        }
    }

    public void reverseVelocity() {
        pointer = pointer == 0 ? 1 : 0;
        setVelocity();
    }

    @Override
    public SimpleEnemyUserData getUserData() {
        return (SimpleEnemyUserData) body.getUserData();
    }
}
