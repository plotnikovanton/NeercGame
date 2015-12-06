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

    public static boolean checkBodyAndBody(Function<Body, Boolean> bodyACheck,
                                           Function<Body, Boolean> bodyBCheck,
                                           Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        return (bodyACheck.apply(a) && bodyBCheck.apply(b)) ||
                bodyACheck.apply(b) && bodyBCheck.apply(a);
    }

    public static boolean checkFixtureAndFixture(Function<Fixture, Boolean> fixtureACheck,
                                                 Function<Fixture, Boolean> fixtureBCheck,
                                                 Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        return (fixtureACheck.apply(a) && fixtureBCheck.apply(b)) ||
                fixtureACheck.apply(b) && fixtureBCheck.apply(a);
    }
    public static boolean bodyIs(UserDataType type, Body body) {
        if (! (body.getUserData() instanceof  UserData)) return false;
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == type;
    }

    public static boolean fixtureIs(UserDataType type, Fixture fixture) {
        if (! (fixture.getUserData() instanceof  UserData)) return false;
        UserData userData = (UserData) fixture.getUserData();
        return userData != null && userData.getUserDataType() == type;

    }

    public static <E> boolean compareFixtureByUserData (Fixture some, E option) {
        E op = (E) some.getUserData();
        return op == option;
    }

    public static <E> boolean compareBodyByUserData (Body some, E option) {
        E op = (E) some.getUserData();
        return op == option;
    }

    public static Fixture getFixture(Contact contact, Function<Fixture, Boolean> checker) {
        if (checker.apply(contact.getFixtureA())) {
            return contact.getFixtureA();
        } else {
            return contact.getFixtureB();
        }
    }

    public static Body getBody(Contact contact, Function<Body, Boolean> checker) {
        if (checker.apply(contact.getFixtureA().getBody())) {
            return contact.getFixtureA().getBody();
        } else {
            return contact.getFixtureB().getBody();
        }
    }

    // Fixtures
    public static Function<Fixture, Boolean> isFixtureFoot = x -> fixtureIs(UserDataType.FOOT, x);
    public static Function<Fixture, Boolean> isFixtureFinish = x -> fixtureIs(UserDataType.FNISH, x);
    public static Function<Fixture, Boolean> isFixturePlatform = x -> fixtureIs(UserDataType.PLATFORM, x);
    public static Function<Fixture, Boolean> isFixtureBug = x -> fixtureIs(UserDataType.BUG, x);
    public static Function<Fixture, Boolean> isFixtureRain = x -> fixtureIs(UserDataType.RAIN, x);
    public static Function<Fixture, Boolean> isFixtureCat = x -> fixtureIs(UserDataType.CAT, x);
    public static Function<Fixture, Boolean> isFixturePlayer = x -> fixtureIs(UserDataType.PLAYER, x);

    // Bodies
    public static Function<Body, Boolean> isBodyPlayer = x -> bodyIs(UserDataType.PLAYER, x);
    public static Function<Body, Boolean> isBodyGround = x -> bodyIs(UserDataType.GROUND, x);
    public static Function<Body, Boolean> isBodySimpleEnemy = x -> bodyIs(UserDataType.SIMPLE_ENEMY, x);
    public static Function<Body, Boolean> isBodyDissapearObject = x -> bodyIs(UserDataType.DISSAPEAR, x);
    public static Function<Body, Boolean> isBodyCat = x -> bodyIs(UserDataType.CAT, x);
    public static Function<Body, Boolean> isBodyEgor = x -> bodyIs(UserDataType.EGOR, x);
}
