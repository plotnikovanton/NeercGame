package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.physics.box2d.Contact;
import com.kaliwe.neercgame.actors.DisappearObject;
import com.kaliwe.neercgame.box2d.DisappearUserData;
import com.kaliwe.neercgame.utils.ContactUtils;

/**
 * Created by anton on 01.12.15.
 */
public class Level0 extends GameStage {
    public Level0() {
        super("Level0.tmx");
        getCamera().viewportHeight *= 2;
        getCamera().viewportWidth *= 2;
    }



    @Override
    public void beginContact(Contact contact) {
        if (ContactUtils.checkFixtureAndBody(
                ContactUtils.isFixtureFoot, ContactUtils.isBodyDissapearObject, contact)) {
            DisappearObject dis;
            if (contact.getFixtureA().getBody().getUserData() instanceof DisappearUserData) {
                dis = ((DisappearUserData) contact.getFixtureA().getBody().getUserData()).getActor();
            } else {
                dis = ((DisappearUserData) contact.getFixtureB().getBody().getUserData()).getActor();
            }
            dis.actWith(player);
        }
    }
}
