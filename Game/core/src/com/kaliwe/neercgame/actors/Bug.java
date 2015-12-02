package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.kaliwe.neercgame.box2d.BugUserData;
import com.kaliwe.neercgame.box2d.UserData;

import java.util.Random;

/**
 * Created by anton on 01.12.15.
 */
public class Bug extends GameActor {
    private static Random rnd = new Random();
    private Animation[] animations = new Animation[2];
    private float animationTime = 5000;
    private boolean roll = false;
    private float rollTime = 0;
    private static float frameTime = 0.06f;
    private float acc = 0;
    private int pos = 0;
    private float drag = 0;
    private float size;
    private boolean right = true;

    public Bug(Body body) {
        super(body);
        ((BugUserData)body.getUserData()).actor = this;
        Texture texture = new Texture("bug.png");
        TextureRegion[][] split = TextureRegion.split(texture, 20, 20);
        size = 0.5f + rnd.nextFloat() * 0.5f;
        animations[0] = new Animation(frameTime, split[0]);
        animations[1] = new Animation(frameTime, split[1]);
    }

    @Override
    public UserData getUserData() {
        return (BugUserData) body.getUserData();
    }

    @Override
    public void act(float delta) {
        acc += delta;
        drag -= delta;
        if (drag < 0) {
            drag = 0.1f;
            pos = rnd.nextFloat() > 0.15 ? 0 : 1;
        }
        if (!roll) {
            if ((int)acc % 5 == 0) {
                if (rnd.nextFloat() < 0.1) {
                    roll = true;
                    right = rnd.nextBoolean();
                    rollTime = 3 * frameTime + rnd.nextFloat() * 8 * frameTime;
                }
            }
        } else {
            if (rollTime > 0) {
                rollTime -= delta;
                animationTime = right ? animationTime + delta : animationTime - delta;
            } else {
                roll = false;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(animations[pos].getKeyFrame(animationTime, true),
                body.getPosition().x - size / 2,
                body.getPosition().y - size / 2,
                size,
                size);
    }
}
