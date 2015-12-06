package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.kaliwe.neercgame.box2d.CatUserData;
import com.kaliwe.neercgame.utils.ResourceUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by anton on 05.12.15.
 */
public class Cat extends GameActor {
    private static final float SPEED = 3f;
    private float animationTime = 0;
    private float totalTime = 1;
    private float tailTime = 0;
    protected static Random rnd = new Random();
    private float goal;
    private enum  CatState {
        WALK,
        SITS,
        TAIL
    }
    private CatState state;
    private boolean right;
    protected static Map<CatState, Animation> animationsRight;
    protected static Map<CatState, Animation> animationsLeft;

    static {
        animationsLeft = new HashMap<>();
        animationsRight = new HashMap<>();

        animationsRight.put(CatState.WALK, ResourceUtils.getAnimation("catWalkRight"));
        animationsLeft.put(CatState.WALK, ResourceUtils.getAnimation("catWalkLeft"));

        animationsRight.put(CatState.SITS, ResourceUtils.getAnimation("catSitsRight"));
        animationsLeft.put(CatState.SITS, ResourceUtils.getAnimation("catSitsLeft"));

        animationsRight.put(CatState.TAIL, ResourceUtils.getAnimation("catTailRight"));
        animationsLeft.put(CatState.TAIL, ResourceUtils.getAnimation("catTailLeft"));
    }

    public Cat(Body body) {
        super(body);
        state = CatState.WALK;
        getUserData().cat = this;
        goal = getUserData().b;
        right = getUserData().a < getUserData().b;
        body.setLinearVelocity(Math.signum(goal - body.getPosition().x) * SPEED, body.getLinearVelocity().y);
    }

    @Override
    public void act(float delta) {
        totalTime += delta;
        animationTime += delta;
        if (Math.abs(goal - body.getPosition().x) < 0.05f) {
            goal = goal == getUserData().a ? getUserData().b : getUserData().a;
            right = !right;
            body.setLinearVelocity(Math.signum(goal - body.getPosition().x) * SPEED, body.getLinearVelocity().y);
        }

        if ((long)totalTime % 10 == 0 && state == CatState.WALK) {
            if (rnd.nextDouble() < 0.1) {
                state = CatState.SITS;
                animationTime = 0;
            }
        }

        if (state == CatState.SITS) {
            if (animationTime > animationsRight.get(CatState.SITS).getAnimationDuration()){
                animationTime = 0;
                state = CatState.TAIL;
                tailTime = 1f + rnd.nextFloat()*3;
            }
        } else if (state == CatState.TAIL) {
            if (animationTime > tailTime) {
                state = CatState.WALK;
                animationTime = 0;
                body.setLinearVelocity(Math.signum(goal - body.getPosition().x) * SPEED, body.getLinearVelocity().y);
            }
        }
        if (state == CatState.WALK) {
        } else {
            body.setLinearVelocity(0, 0);
        }

    }

    @Override
    public void draw(Batch sb, float a) {
        Animation cur = right ? animationsRight.get(state) : animationsLeft.get(state);
        TextureRegion curReg = cur.getKeyFrame(animationTime, true);
        float width = 26f/25f;//26;
        float height = 22f/25f;//22;
        sb.draw(curReg, body.getPosition().x - width / 2, body.getPosition().y - height / 2, width, height);
    }

    @Override
    public CatUserData getUserData() {
        return (CatUserData) body.getUserData();
    }
}
