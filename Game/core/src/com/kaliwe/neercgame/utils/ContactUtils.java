package com.kaliwe.neercgame.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.kaliwe.neercgame.box2d.UserData;
import com.kaliwe.neercgame.enums.UserDataType;

import java.util.function.Function;

/**
 * Created by anton on 18.11.15.
 */
public class ContactUtils {
    public static boolean checkFixtureAndBody(Function<Fixture, Boolean> fixtureCheck,
                                              Function<Body, Boolean> bodyCheck,
                                              Contact contact) {

        Fixture af = contact.getFixtureA();
        Body ab = af.getBody();
        Fixture bf = contact.getFixtureB();
        Body bb = bf.getBody();

        return  (bodyCheck.apply(ab) && fixtureCheck.apply(bf)) ||
                (fixtureCheck.apply(af) && bodyCheck.apply(bb));
    }

    public static boolean bodyIs(UserDataType type, Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == type;
    }

    public static boolean fixtureIs(UserDataType type, Fixture fixture) {
        UserData userData = (UserData) fixture.getUserData();
        return userData != null && userData.getUserDataType() == type;
    }

    // Fixtures
    public static Function<Fixture, Boolean> isFixtureFoot = x -> fixtureIs(UserDataType.FOOT, x);

    // Bodies
    public static Function<Body, Boolean> isBodyPlayer = x -> bodyIs(UserDataType.PLAYER, x);
    public static Function<Body, Boolean> isBodyGround = x -> bodyIs(UserDataType.GROUND, x);
    public static Function<Body, Boolean> isBodySimpleEnemy = x -> bodyIs(UserDataType.SIMPLE_ENEMY, x);
}
