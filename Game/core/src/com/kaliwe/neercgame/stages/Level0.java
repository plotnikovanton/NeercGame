package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.kaliwe.neercgame.actors.DisappearObject;
import com.kaliwe.neercgame.box2d.DisappearUserData;
import com.kaliwe.neercgame.utils.ContactUtils;
import com.kaliwe.neercgame.utils.WorldUtils;

/**
 * Created by anton on 01.12.15.
 */
public class Level0 extends GameStage {
    public Level0() {
        super("level0.tmx");
        getCamera().viewportHeight *= 2;
        getCamera().viewportWidth *= 2;
        cameraLowerY = 8f;
    }


    private void setupEnemies() {
        for (Body b : WorldUtils.createDisappearObjects(world, mapHolder.map)) {
            addActor(new DisappearObject(b));
        }
    }

    @Override
    public void setupWorld(String mapName) {
        super.setupWorld(mapName);
        setupEnemies();
    }

    @Override
    public void beginContact(Contact contact) {
        super.beginContact(contact);
        if (ContactUtils.checkFixtureAndBody(
                ContactUtils.isFixtureFoot, ContactUtils.isBodyDissapearObject, contact)) {
            DisappearObject dis;
            // TODO: Replace using ContactUtils
            if (contact.getFixtureA().getBody().getUserData() instanceof DisappearUserData) {
                dis = ((DisappearUserData) contact.getFixtureA().getBody().getUserData()).getActor();
            } else {
                dis = ((DisappearUserData) contact.getFixtureB().getBody().getUserData()).getActor();
            }
            dis.actWith(player);
        }
    }
}
