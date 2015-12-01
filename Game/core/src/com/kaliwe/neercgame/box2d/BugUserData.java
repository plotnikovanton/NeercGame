package com.kaliwe.neercgame.box2d;

import com.kaliwe.neercgame.enums.UserDataType;

/**
 * Created by anton on 01.12.15.
 */
public class BugUserData extends UserData {
    public BugUserData() {
        userDataType = UserDataType.BUG;
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
