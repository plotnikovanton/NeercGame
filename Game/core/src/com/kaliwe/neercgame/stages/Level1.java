package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.kaliwe.neercgame.actors.Bug;
import com.kaliwe.neercgame.box2d.BugUserData;
import com.kaliwe.neercgame.utils.Background;
import com.kaliwe.neercgame.utils.ContactUtils;
import com.kaliwe.neercgame.utils.Mask;
import com.kaliwe.neercgame.utils.WorldUtils;

/**
 * Created by anton on 01.12.15.
 */
public class Level1 extends GameStage {
    private short score;
    private Background bg;

    public Level1() {
        super("level1.tmx");
        bg = new Background(
                new TextureRegion(new Texture("buildings.png")),
                0.1f, 0.5f, hudCam, 0, 0);
        score = 0;
    }

    @Override
    public void setupWorld(String mapName) {
        super.setupWorld(mapName);
        WorldUtils.createPlatforms(world, mapHolder.map);
        for (Body b : WorldUtils.createBugs(world, mapHolder.map)) {
            addActor(new Bug(b));
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
        }
    }

    @Override
    public void draw() {
        bg.draw(getBatch(), getCamera().position);
        super.draw();
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
