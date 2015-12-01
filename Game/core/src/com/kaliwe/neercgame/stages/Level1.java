package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Filter;
import com.kaliwe.neercgame.utils.ContactUtils;
import com.kaliwe.neercgame.utils.Mask;
import com.kaliwe.neercgame.utils.WorldUtils;

/**
 * Created by anton on 01.12.15.
 */
public class Level1 extends GameStage {
    public Level1() {
        super("level1.tmx");
    }

    @Override
    public void setupWorld(String mapName) {
        super.setupWorld(mapName);
        WorldUtils.createPlatforms(world, mapHolder.map);
    }

    @Override
    public void beginContact(Contact contact) {
        super.beginContact(contact);

        if (ContactUtils.checkFixtureAndFixture(ContactUtils.isFixtureFoot, ContactUtils.isFixturePlatform, contact)) {
            player.incNumOfFootContacts();
            Filter filter = new Filter();
            filter.maskBits = Mask.PLAYER;
            if (ContactUtils.isFixturePlatform.apply(contact.getFixtureA())) {
                contact.getFixtureA().setFilterData(filter);
            } else {
                contact.getFixtureB().setFilterData(filter);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        super.endContact(contact);
        if (ContactUtils.checkFixtureAndFixture(ContactUtils.isFixtureFoot, ContactUtils.isFixturePlatform, contact)) {
            player.decNumOfFootContacts();
            Filter filter = new Filter();
            filter.maskBits = ~Mask.PLAYER;
            if (ContactUtils.isFixturePlatform.apply(contact.getFixtureA())) {
                contact.getFixtureA().setFilterData(filter);
            } else {
                contact.getFixtureB().setFilterData(filter);
            }
        }
    }
}
