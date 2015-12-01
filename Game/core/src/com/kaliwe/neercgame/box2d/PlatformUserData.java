package com.kaliwe.neercgame.box2d;

import com.kaliwe.neercgame.enums.UserDataType;

/**
 * Created by anton on 01.12.15.
 */
public class PlatformUserData extends UserData{
    public PlatformUserData() {
        userDataType = UserDataType.PLATFORM;
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
