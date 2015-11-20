package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.math.Rectangle;
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
    protected Rectangle screenRectangle;

    public GameActor(Body body) {
        this.body = body;
        this.userData = (UserData) body.getUserData();
        screenRectangle = new Rectangle();
    }

    private void updateRectangle() {
        screenRectangle.x = transformToScreen(body.getPosition().x );
        screenRectangle.y = transformToScreen(body.getPosition().y );
    }
    public abstract UserData getUserData();
    protected float transformToScreen(float n) {
        return Constants.PPM * n;
    }
}
