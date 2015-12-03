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
        //TODO: replace with ResourceUtils
        super("level0");
        getCamera().viewportHeight *= 2;
        getCamera().viewportWidth *= 2;
        cameraLowerY = 8f;
        score = 1;
        maxScore = 1;
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
            DisappearUserData data = ((DisappearUserData)ContactUtils.getBody(contact, ContactUtils.isBodyDissapearObject).getUserData());
            data.getActor().actWith(player);
        }
    }

    @Override
    public void draw() {
        super.draw();
    }
}
