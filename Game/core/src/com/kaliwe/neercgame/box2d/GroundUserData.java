package com.kaliwe.neercgame.box2d;

import com.kaliwe.neercgame.enums.UserDataType;

/**
 * Created by anton on 18.11.15.
 */
public class GroundUserData extends UserData {
    public GroundUserData() {
        super();
        userDataType = UserDataType.GROUND;
    }

    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public float getHeight() {
        return 0;
    }
}
