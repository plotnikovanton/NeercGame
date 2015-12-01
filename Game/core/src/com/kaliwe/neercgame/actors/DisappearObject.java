package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.kaliwe.neercgame.box2d.DisappearUserData;
import com.kaliwe.neercgame.enums.PlayerState;
import com.kaliwe.neercgame.utils.Constants;

import java.util.Arrays;
import java.util.HashMap;
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

    private final Map<State, Animation> animations = new HashMap<>();
    private final boolean isLamp;
    private State state;
    private float animationTime;
    private static float FRAME_DURATION = 0.1f;
    private final static TextureRegion[][] split = TextureRegion.split(new Texture("logo.png"), 55, 101);

    public DisappearObject(Body body) {
        super(body);
        switch (getUserData().getName()) {
            case "cloud":
                animations.put(State.INIT, new Animation(10f, split[5][0]));
                animations.put(State.TOUCHED, new Animation(10f, split[6][0]));
                animations.put(State.BROKEN, new Animation(0.1f, Arrays.copyOfRange(split[7], 0, 4)));
                animations.get(State.BROKEN).getKeyFrames()[3] = split[5][0];
                isLamp = false;
                break;
            case "lamp":
                animations.put(State.INIT, new Animation(10f, split[0][0]));
                animations.put(State.TOUCHED, new Animation(10f, split[1][0]));
                animations.put(State.BROKEN, new Animation(10f, split[1][0]));
                isLamp = true;
                break;
            case "balloon":
                animations.put(State.INIT, new Animation(10f, split[2][0]));
                animations.put(State.TOUCHED, new Animation(10f, split[3][0]));
                animations.put(State.BROKEN, new Animation(0.1f, Arrays.copyOfRange(split[4], 0, 4)));
                animations.get(State.BROKEN).getKeyFrames()[3] = split[2][0];
                isLamp = false;
                break;
            default:
                isLamp = false;
        }

        getUserData().setActor(this);

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
                player.setLinearVelocity(0, 0);
                player.jumpOutOfEnemy();
                if (isLamp) {
                    player.setState(PlayerState.BURNING);
                } else {
                    player.setState(PlayerState.DEAD);
                }
                    state = State.BROKEN;
                getUserData().isFlaggedForDelete = true;
                animationTime = 0;
                break;
        }
    }

    @Override
    public void act(float delta) {
        //int length = animations.get(state).getKeyFrames().length;
        //animationTime = animationTime + delta > length * animations.get(state).getAnimationDuration() ?
        //                length * animations.get(state).getAnimationDuration() :
        //                animationTime + delta;
        animationTime += delta;
    }

    @Override
    public void draw(Batch b, float pa) {
        float width = Constants.DISAPPEAR_OBJECT_WIDTH * 1.25f;
        TextureRegion frame = animations.get(state).getKeyFrame(animationTime, false);
        b.draw(frame,
                body.getPosition().x - width / 2 + 0.42f,
                body.getPosition().y - 4.9f,
                width,
                width * frame.getRegionHeight() / frame.getRegionWidth()
                );
    }

    @Override
    public DisappearUserData getUserData() {
        return (DisappearUserData) body.getUserData();
    }
}
