package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.kaliwe.neercgame.actors.Bug;
import com.kaliwe.neercgame.actors.Pig;
import com.kaliwe.neercgame.enums.Special;
import com.kaliwe.neercgame.screens.GameStateManager;
import com.kaliwe.neercgame.utils.*;

/**
 * Created by anton on 04.12.15.
 */
public class BetweenStagesStage extends GameStage {
    BitmapFont font;
    OrthographicCamera textCamera;

//    protected static Sound sound;
//    protected static long soundId;
//    static {
//        sound = ResourceUtils.getSound("hallOfFame");
//        soundId = sound.loop();
//        sound.setVolume(soundId, 0.1f);
//        sound.pause(soundId);
//    }

    Pig pig;
    Bug bug;
    Vector2 cup;
    float dst;

    TextureRegion bg ;

    short score;
    short maxScore;
    String text;

    public BetweenStagesStage(short score, short maxScore, String text) {
        super("levelBetweenLevels", 24, "Hah, lol");

        this.score= score;
        this.maxScore = maxScore;
        this.text = text;

        textCamera = new OrthographicCamera();
        textCamera.viewportHeight = VIEWPORT_HEIGHT * 30;
        textCamera.viewportWidth = VIEWPORT_WIDTH * 30;

        cameraLowerY = 8.5f;
        getCamera().position.set(10, 8.5f, 0);
        font = ResourceUtils.getFont("visitor");

        bg = ResourceUtils.getTextureRegion(text);

        sound.resume(soundId);
//        super.sound.pause(super.soundId);
    }

    @Override
    public void setupWorld(String mapName) {
        world = WorldUtils.createWorld();
        setupMap(mapName);

        TiledMap map = mapHolder.map;
        MapObjects objects = map.getLayers().get("special").getObjects();
        // Create pig
        Ellipse ellipse = ((EllipseMapObject)objects.get("pig")).getEllipse();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.gravityScale = 20f;
        bodyDef.position.set(ellipse.x / Constants.PPM, ellipse.y / Constants.PPM);
        Shape shape = new CircleShape();
        shape.setRadius(0.5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;

        Body bodyPig = world.createBody(bodyDef);
        bodyPig.createFixture(fixtureDef).setUserData(Special.PIG);
        bodyPig.setAwake(false);
        pig = new Pig(bodyPig);
        addActor(pig);

        // Create bug
        ellipse = ((EllipseMapObject)objects.get("bug")).getEllipse();
        bodyDef.position.set(ellipse.x / Constants.PPM, ellipse.y / Constants.PPM);
        Body bugBody = world.createBody(bodyDef);
        bugBody.createFixture(fixtureDef).setUserData(Special.BUG);
        bugBody.setAwake(false);
        bug = new Bug(bugBody);
        addActor(bug);

        // Create cup
        ellipse = ((EllipseMapObject) objects.get("cup")).getEllipse();
        cup = new Vector2(ellipse.x / Constants.PPM, ellipse.y / Constants.PPM);
    }


    @Override
    public void act(float delta){
        super.act(delta);
        textCamera.position.set(getCamera().position.x * 30, getCamera().position.y, 0);
        textCamera.update();
        dst = Math.abs(player.getPosition().x - cup.x);
        if (!bug.getBody().isAwake() && dst < 2) {
            bug.getBody().setAwake(true);
            pig.getBody().setAwake(true);
        }
    }

    @Override
    public void draw() {
        super.draw();

        getBatch().setProjectionMatrix(textCamera.combined);
        getBatch().begin();
        font.setColor(Color.GREEN);
        font.draw(getBatch(), "problem solved!!", 25, 80);
        font.draw(getBatch(), "total score: " +
                HUDUtils.totalScoreFormatter.format(GameStateManager.totalScore + (double) score / (double) maxScore), 10, 60);
        font.draw(getBatch(), "total time: " +
                HUDUtils.simpleDateFormat.format(GameStateManager.totalTime * 1000), 10, 50);
        font.draw(getBatch(), "bugs on level: " + score+"/"+maxScore, 10, 30);

        font.setColor(Color.BLACK);
        font.draw(getBatch(), "debug", bug.getBody().getPosition().x * 30, bug.getBody().getPosition().y * 30 - cameraLowerY * 30 + 40, 0, 1, true);
        font.draw(getBatch(), "next problem", pig.getBody().getPosition().x * 30, pig.getBody().getPosition().y * 30 - cameraLowerY * 30 + 40, 0, 1, true);

        getBatch().end();

        if (dst < 6) {
            HUDUtils.drawTextBox(getBatch(), hudCam, Math.max(0, Math.min(1, 1-dst/6)), bg);
        }


    }

    @Override
    public void beginContact(Contact contact) {
        super.beginContact(contact);
        if (ContactUtils.checkFixtureAndBody(x->ContactUtils.compareFixtureByUserData(x, Special.BUG),
        ContactUtils.isBodyPlayer, contact)) {
            sound.pause(soundId);
            GameStateManager.failed(score, maxScore);
        } else if (ContactUtils.checkFixtureAndBody(x->ContactUtils.compareFixtureByUserData(x, Special.PIG),
                ContactUtils.isBodyPlayer, contact)) {
            sound.pause(soundId);
            GameStateManager.sureComplete(score, maxScore);
        }
    }

    @Override
    protected void updateCamera() {
        float camPosX;
        if (player.getPosition().x > bug.getBody().getPosition().x) {
            camPosX = cup.x;
        } else {
            camPosX = Math.max(player.getPosition().x, mapHolder.spawn.x + VIEWPORT_WIDTH / 4) ;
        }
        float camPosY = Math.max(cameraLowerY, player.getPosition().y);

        getCamera().position.lerp(new Vector3(camPosX, camPosY, 0f), 0.11f);

        getCamera().update();
        tiledMapRenderer.setView((OrthographicCamera) getCamera());
    }
}
