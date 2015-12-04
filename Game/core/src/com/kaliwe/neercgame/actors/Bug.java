package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.kaliwe.neercgame.box2d.BugUserData;
import com.kaliwe.neercgame.box2d.UserData;
import com.kaliwe.neercgame.utils.ResourceUtils;

import java.util.Random;

/**
 * Created by anton on 01.12.15.
 */
public class Bug extends GameActor {
    protected static Random rnd = new Random();
    protected Animation[] animations = new Animation[2];
    protected float animationTime = 5000;
    protected boolean roll = false;
    protected float rollTime = 0;
    protected static float frameTime = 0.06f;
    protected float acc = 0;
    protected int pos = 0;
    protected float drag = 0;
    protected float size;
    protected boolean right = true;

    public Bug(Body body) {
        super(body);
        BugUserData userData = ((BugUserData)body.getUserData());
        if (userData!=null)userData.actor = this;
        Texture texture = new Texture("bug.png");
        TextureRegion[][] split = TextureRegion.split(texture, 20, 20);
        size = 0.5f + rnd.nextFloat() * 0.5f;
        animations[0] = ResourceUtils.getAnimation("bug0");
        animations[1] = ResourceUtils.getAnimation("bug1");
        frameTime = animations[0].getFrameDuration();
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
