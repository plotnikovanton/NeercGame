package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.kaliwe.neercgame.box2d.UserData;
import com.kaliwe.neercgame.utils.ResourceUtils;

import java.util.Random;

/**
 * Created by anton on 04.12.15.
 */
public class Pig extends GameActor {
    protected static Random rnd = new Random();
    protected Animation animation;
    protected TextureRegion tr1;
    protected TextureRegion tr2;
    protected float animationTime = 0;
    protected boolean playAnimation = false;
    protected float animationTotalTime = 0;
    protected float acc = 0;
    protected boolean pos;
    protected float drag = 0;
    protected float size;

    public Pig(Body body) {
        super(body);
        size = 1f;
        animation = ResourceUtils.getAnimation("pigSpecial");
        tr1 = ResourceUtils.getTextureRegion("pigNormal1");
        tr2 = ResourceUtils.getTextureRegion("pigNormal2");
    }

    @Override
    public UserData getUserData() {
        return null;
    }

    @Override
    public void act(float delta) {
        acc += delta;
        if (!playAnimation) {
            drag -= delta;
            if (drag < 0) {
                drag = 0.1f;
                pos = rnd.nextFloat() > 0.15;
            }
            if ((int)acc % 8 == 0) {
                if (rnd.nextFloat() < 0.1) {
                    animationTime = 1 + rnd.nextFloat()*2;
                    playAnimation = true;
                }
            }
        } else {
            animationTime -= delta;
            if (animationTime < 0) {
                playAnimation = false;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (playAnimation) {
            batch.draw(animation.getKeyFrame(acc, true),
                    body.getPosition().x - size / 2,
                    body.getPosition().y - size / 2,
                    size,
                    size);
        } else {
            TextureRegion textureRegion = pos ? tr1 : tr2;
            batch.draw(textureRegion,
                    body.getPosition().x - size / 2,
                    body.getPosition().y - size / 2,
                    size,
                    size);
        }
    }
}
