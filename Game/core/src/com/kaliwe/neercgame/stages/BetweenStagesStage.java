package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.kaliwe.neercgame.actors.Bug;
import com.kaliwe.neercgame.actors.Pig;
import com.kaliwe.neercgame.enums.Special;
import com.kaliwe.neercgame.screens.GameScreen;
import com.kaliwe.neercgame.screens.GameStateManager;
import com.kaliwe.neercgame.utils.*;

/**
 * Created by anton on 04.12.15.
 */
public class BetweenStagesStage extends GameStage {
    BitmapFont font;
    OrthographicCamera textCamera;

    Pig pig;
    Bug bug;
    Vector2 cup;
    float dst;

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
        font.draw(getBatch(), "level complete!!", 25, 80);
        font.draw(getBatch(), "total score: " + GameStateManager.totalScore + (double) score / (double) maxScore, 10, 60);
        font.draw(getBatch(), "total time: " + GameScreen.getTotalTime(), 10, 50);
        font.draw(getBatch(), "bugs on level: " + score+"/"+maxScore, 10, 30);


        getBatch().end();

        if (dst < 6) {
            HUDUtils.drawTextBox(getBatch(), hudCam, text, 0.8f-dst/6);
        }


    }

    @Override
    public void beginContact(Contact contact) {
        super.beginContact(contact);
        if (ContactUtils.checkFixtureAndBody(x->ContactUtils.compareFixtureByUserData(x, Special.BUG),
        ContactUtils.isBodyPlayer, contact)) {
            GameStateManager.failed(score, maxScore);
        } else if (ContactUtils.checkFixtureAndBody(x->ContactUtils.compareFixtureByUserData(x, Special.PIG),
                ContactUtils.isBodyPlayer, contact)) {
            GameStateManager.sureComplete(score, maxScore);
        }
    }
}
