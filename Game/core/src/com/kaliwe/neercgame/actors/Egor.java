package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.kaliwe.neercgame.box2d.EgorUserData;
import com.kaliwe.neercgame.utils.ResourceUtils;

import java.util.Random;

/**
 * Created by anton on 06.12.15.
 */
public class Egor extends GameActor {
    private static final float SPEED = 3f;
    protected static Random rnd = new Random();
    private float animationTime = 0;
    private float totalTime = 1;
    private float standingTime = 0;
    private float goal;
    private boolean right;
    protected static Animation animationRight;
    protected static Animation animationLeft;

    static {
        animationRight = ResourceUtils.getAnimation("egorRight");
        animationLeft = ResourceUtils.getAnimation("egorLeft");
    }

    public Egor(Body body) {
        super(body);
        goal = getUserData().b;
        right = getUserData().a < getUserData().b;
        body.setLinearVelocity(Math.signum(goal - body.getPosition().x) * SPEED, body.getLinearVelocity().y);
    }

    @Override
    public void act(float delta) {
        totalTime += delta;
        if (Math.abs(goal - body.getPosition().x) < 0.05f) {
            goal = goal == getUserData().a ? getUserData().b : getUserData().a;
            right = !right;
        }

        if ((long)totalTime % 10 == 0) {
            if (rnd.nextDouble() < 0.1) {
                standingTime = 1 + rnd.nextFloat();
                animationTime = 0;
                body.setLinearVelocity(0, 0);
            }
        }

        if (standingTime > 0) {
            standingTime -= delta;
        } else {
            animationTime += delta;
            body.setLinearVelocity(Math.signum(goal - body.getPosition().x) * SPEED, body.getLinearVelocity().y);
        }

    }

    @Override
    public void draw(Batch sb, float a) {
        Animation cur = right ? animationRight : animationLeft;
        TextureRegion curReg = cur.getKeyFrame(animationTime, true);
        float width = 26f/25f;//26;
        float height = 22f/25f;//22;
        sb.draw(curReg, body.getPosition().x - width / 2, body.getPosition().y - height / 2, width, height);
    }

    @Override
    public EgorUserData getUserData() {
        return (EgorUserData) body.getUserData();
    }
}
