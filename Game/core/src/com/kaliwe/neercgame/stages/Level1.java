package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.kaliwe.neercgame.actors.Bug;
import com.kaliwe.neercgame.actors.Cat;
import com.kaliwe.neercgame.actors.Egor;
import com.kaliwe.neercgame.actors.RainCloud;
import com.kaliwe.neercgame.box2d.BugUserData;
import com.kaliwe.neercgame.utils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton on 01.12.15.
 */
public class Level1 extends GameStage {
    protected List<Background> bgs = new ArrayList<>();
    protected int[] renderOnMid = {3};
    protected int[] renderOnBg = {0, 1, 2};
    protected List<RainCloud> clouds;
    protected Color tint = null;
    protected Sound collectSmt;



    public Level1() {
        this("level1", "LEVEL 2");
        Gdx.gl.glClearColor(135/255f,206/255f,235/255f,1);

        float skyOffset = 260;
        bgs.add(new Background(ResourceUtils.getTextureRegion("cloudsBack"), 0.4f, 5f, 0.9f, hudCam, 0, skyOffset));
        bgs.add(new Background(ResourceUtils.getTextureRegion("clouds"), 0.5f, 8f, 0.8f, hudCam, 0, skyOffset));
        bgs.add(new Background(ResourceUtils.getTextureRegion("buildings"), 1.5f, 10f , 1f, hudCam, 0, cameraLowerY*14));

    }

    public Level1(String mapName, String text) {
        super(mapName, 12, text);
        cameraLowerY = 17f;
        super.renderOnBg = new int[]{};
        collectSmt = ResourceUtils.getSound("getCoin");
    }

    @Override
    public void setupWorld(String mapName) {
        super.setupWorld(mapName);
        WorldUtils.createPlatforms(world, mapHolder.map);
        // setup Bugs
        setupBugs();

        // setup Rain
        clouds = new ArrayList<>();
        for (RainCloud actor : WorldUtils.createRain(world, mapHolder.map)){
            addActor(actor);
            clouds.add(actor);
        }

        setupCats();

        setupEgors();
    }

    public void setupBugs() {
        maxScore = 0;
        for (Body b : WorldUtils.createBugs(world, mapHolder.map)) {
            addActor(new Bug(b));
            maxScore++;
        }
    }

    protected void setupCats() {
        for (Body body : WorldUtils.createCats(world, mapHolder.map)) {
            addActor(new Cat(body));
        }
    }

    protected void setupEgors() {
        for (Body body : WorldUtils.createEgors(world, mapHolder.map)) {
            addActor(new Egor(body));
        }
    }

    @Override
    public void beginContact(Contact contact) {
        super.beginContact(contact);

        if (ContactUtils.checkFixtureAndFixture(ContactUtils.isFixtureFoot, ContactUtils.isFixturePlatform, contact)) {
            player.incNumOfFootContacts();
            Filter filter = new Filter();
            filter.maskBits = Mask.PLAYER;
            ContactUtils.getFixture(contact, ContactUtils.isFixturePlatform).setFilterData(filter);
        } else if (ContactUtils.checkFixtureAndBody(ContactUtils.isFixtureBug, ContactUtils.isBodyPlayer, contact)) {
            Fixture bug = ContactUtils.getFixture(contact, ContactUtils.isFixtureBug);
            BugUserData userData = (BugUserData) bug.getUserData();
            if (!userData.isFlaggedForDelete) {
                userData.actor.remove();
                score++;
            }
            userData.isFlaggedForDelete = true;

            long sId = collectSmt.play();
            collectSmt.setVolume(sId, 0.5f);

        } else if (ContactUtils.checkFixtureAndBody(ContactUtils.isFixtureRain, ContactUtils.isBodyPlayer, contact)) {
            player.kill();
        } else if (
            ContactUtils.checkBodyAndBody(ContactUtils.isBodyCat, ContactUtils.isBodyPlayer, contact)
                || ContactUtils.checkFixtureAndBody(ContactUtils.isFixturePlayer, ContactUtils.isBodyEgor, contact)) {
            player.kill();
            Sound snd = ResourceUtils.getSound("meow");
            long sId = snd.play();
            //snd.setVolume(sId, 0.5f);
        } else if (ContactUtils.checkFixtureAndBody(ContactUtils.isFixtureFoot, ContactUtils.isBodyEgor, contact)) {
            player.jumpOutOfEnemy();
        }
    }

    @Override
    public void draw() {
        if (tint != null) {
            getBatch().setColor(tint);
        }
        Vector3 position = new Vector3(getCamera().position);
        position.y = position.y * hudCam.viewportHeight / VIEWPORT_HEIGHT;
        for (Background bg : bgs) {
            bg.draw(getBatch(), position);
        }


        tiledMapRenderer.render(renderOnBg);
        clouds.stream().forEach(x -> {
            x.drawRain(getBatch());
            x.draw(getBatch());
        });

        tiledMapRenderer.render(renderOnMid);
        super.draw();
        HUDUtils.drawCollectedBugs(getBatch(), hudCam, score, maxScore);
        HUDUtils.drawTotalScore(getBatch(), hudCam, score, maxScore);
    }

    @Override
    public void endContact(Contact contact) {
        super.endContact(contact);
        if (ContactUtils.checkFixtureAndFixture(ContactUtils.isFixtureFoot, ContactUtils.isFixturePlatform, contact)) {
            player.decNumOfFootContacts();
            Filter filter = new Filter();
            filter.maskBits = ~Mask.PLAYER;
            ContactUtils.getFixture(contact, ContactUtils.isFixturePlatform).setFilterData(filter);
        }
    }
}
