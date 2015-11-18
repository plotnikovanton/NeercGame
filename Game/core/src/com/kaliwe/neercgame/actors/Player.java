package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.kaliwe.neercgame.box2d.PlayerUserData;

/**
 * Created by anton on 18.11.15.
 */
public class Player extends GameActor {
    private boolean jumping;
    private boolean dodging;
    private float speedX = 0f;

    public Player(Body body) {
        super(body);
    }

    @Override
    public PlayerUserData getUserData() {
        return (PlayerUserData)userData;
    }

    public void jump() {
        if (!(jumping || dodging)) {
            body.applyLinearImpulse(getUserData().getJumpingLinearImpulse(), body.getWorldCenter(), true);
            jumping = true;
        }
    }

    public void landed() {
        jumping = false;
    }

    public void dodge() {
        if (!jumping) {
            body.setTransform(getUserData().getDodgePosition(), getUserData().getDodgeAngle());
            dodging = true;
        }
    }

    public void stopDodge() {
        dodging = false;
        body.setTransform(getUserData().getRunningPosition(), 0f);
    }

    public boolean isDodging() {
        return dodging;
    }

    public void addSpeed(float speed) {
        speedX += speed;
        Vector2 v = body.getLinearVelocity();
        v.x = speedX;
        body.setLinearVelocity(v);
    }

    public Vector2 getPosition() {
        return super.body.getPosition();
    }
}
