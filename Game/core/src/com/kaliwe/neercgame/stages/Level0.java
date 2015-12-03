package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.kaliwe.neercgame.actors.DisappearObject;
import com.kaliwe.neercgame.box2d.DisappearUserData;
import com.kaliwe.neercgame.utils.Constants;
import com.kaliwe.neercgame.utils.ContactUtils;
import com.kaliwe.neercgame.utils.WorldUtils;

/**
 * Created by anton on 01.12.15.
 */
public class Level0 extends GameStage {
    public Level0() {
        super("level0", 24);
        Gdx.gl.glClearColor(1,1,1,1);
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
    protected void updateCamera() {
    }

    @Override
    protected void setupCamera() {
        getCamera().viewportWidth = VIEWPORT_WIDTH;
        getCamera().viewportHeight = VIEWPORT_HEIGHT;
        getCamera().position.set(752 / Constants.PPM, 769 / Constants.PPM + 5,0);
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
