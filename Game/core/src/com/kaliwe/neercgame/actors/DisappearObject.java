package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.kaliwe.neercgame.box2d.DisappearUserData;

import java.util.Map;

/**
 * Created by anton on 30.11.15.
 */
public class DisappearObject extends GameActor{
    enum State {
        INIT,
        TOUCHED,
        BROKEN
    }

    private Map<State, Animation> animations;
    private State state;
    private float animationTime;
    private static float FRAME_DURATION = 0.1f;

    public DisappearObject(Body body) {
        super(body);

        switch (getUserData().getName()) {
            case "cloud":
                
                break;
        }

        animationTime = 0;
        state = State.INIT;

    }

    public void actWith(Player player) {
        switch (state) {
            case INIT:
                player.jumpOutOfEnemy();
                state = State.TOUCHED;
                animationTime = 0;
                break;
            case TOUCHED:
                player.jumpOutOfEnemy();
                state = State.BROKEN;
                body.getWorld().destroyBody(body);
                animationTime = 0;
                break;
        }
    }

    @Override
    public void act(float delta) {
        animationTime += delta;
    }

    @Override
    public void draw(Batch b, float pa) {
        TextureRegion frame = animations.get(state).getKeyFrame(animationTime, false);
        b.draw(frame,
               body.getPosition().x,
               body.getPosition().y);
    }

    @Override
    public DisappearUserData getUserData() {
        return (DisappearUserData) body.getUserData();
    }
}
