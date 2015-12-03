package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kaliwe.neercgame.box2d.RainUserData;
import com.kaliwe.neercgame.box2d.UserData;
import com.kaliwe.neercgame.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by anton on 03.12.15.
 */
public class RainCloud extends Actor {
    private Vector2 position;
    private float time = 0;
    private float frequency = 1f;
    private final World world;
    private List<Body> bodyes = new ArrayList<>();

    private TextureRegion cloudTR = ResourceUtils.getTextureRegion("rainyCloud");
    private TextureRegion rainAnimation = ResourceUtils.getTextureRegion("rain");
    private static float rainW;
    private static float rainH;


    private static final FixtureDef fixtureDef = new FixtureDef();
    static {
        PolygonShape shape = new PolygonShape();
        float sizeMultiplier = 0.026f;
        rainH = sizeMultiplier * 19 - 0.1f;
        rainW = sizeMultiplier * 58;
        shape.setAsBox(rainW, rainH);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

    }

    private final BodyDef bodyDef = new BodyDef();

    public RainCloud(World world, Vector2 position) {
        this.position = position;
        this.world = world;

        bodyDef.position.set(position.x, position.y - 0.1f);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.gravityScale = 8f;
    }

    @Override
    public void act(float delta) {
        time += delta;
        if (time > frequency) {
            time = 0;
            UserData userData = new RainUserData();
            Body body = world.createBody(bodyDef);
            body.createFixture(fixtureDef).setUserData(userData);
            body.setUserData(userData);
            bodyes.add(body);
        }

        for (Iterator<Body> iter = bodyes.iterator(); iter.hasNext(); ) {
            Body x = iter.next();
            if (x.getPosition().y < -20) {
                ((UserData) x.getUserData()).isFlaggedForDelete = true;
                iter.remove();
            }
        }
    }

    public void draw(Batch batch) {
        batch.setProjectionMatrix(this.getStage().getCamera().combined);
        batch.begin();
        float textureSizeMultiplier = 0.07f;
        float width = cloudTR.getRegionWidth() * textureSizeMultiplier;
        float height = cloudTR.getRegionHeight() * textureSizeMultiplier;
        batch.draw(cloudTR, position.x - width/2, position.y - height/2, width, height);
        batch.end();
    }

    public void drawRain(Batch batch) {
        batch.setProjectionMatrix(this.getStage().getCamera().combined);
        batch.begin();
        for (Body body : bodyes) {
            batch.draw(rainAnimation, body.getPosition().x - rainW ,
                    body.getPosition().y - rainH , rainW * 2, rainH * 2);
        }
        batch.end();
    }
}
