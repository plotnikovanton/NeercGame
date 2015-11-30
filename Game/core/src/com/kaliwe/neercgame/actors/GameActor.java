package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kaliwe.neercgame.box2d.UserData;
import com.kaliwe.neercgame.utils.Constants;

/**
 * Created by anton on 18.11.15.
 */
public abstract class GameActor extends Actor {
    protected Body body;
    protected UserData userData;

    public GameActor(Body body) {
        this.body = body;
        this.userData = (UserData) body.getUserData();
    }

    public abstract UserData getUserData();
    protected float transformToScreen(float n) {
        return Constants.PPM * n;
    }

    public float getOffsetX() {
        UserData userData = (UserData)body.getUserData();
        return body.getPosition().x - userData.getWidth() / 2;
    }

    public float getOffsetY() {
        UserData userData = (UserData) body.getUserData();
        return body.getPosition().y - userData.getHeight() / 2 - 0.1f;
    }

    public float getWidth() {
        UserData userData = (UserData) body.getUserData();
        return userData.getWidth();
    }

    public float getHeight() {
        UserData userData = (UserData) body.getUserData();
        return userData.getHeight();
    }
}
