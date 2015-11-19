package com.kaliwe.neercgame.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.kaliwe.neercgame.box2d.UserData;
import com.kaliwe.neercgame.enums.UserDataType;

/**
 * Created by anton on 18.11.15.
 */
public class BodyUtils {
    public static boolean bodyIsPlayer (Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.PLAYER;
    }

    public static boolean fixtureIsFoot (Fixture fixture) {
        UserData userData = (UserData) fixture.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.FOOT;
    }

    public static boolean bodyIsGround (Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.GROUND;
    }
}
