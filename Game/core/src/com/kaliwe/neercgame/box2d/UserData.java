package com.kaliwe.neercgame.box2d;

import com.kaliwe.neercgame.enums.UserDataType;

/**
 * Created by anton on 18.11.15.
 */
public abstract class UserData {
    protected UserDataType userDataType;

    public UserData() {
    }

    public UserDataType getUserDataType() {
        return userDataType;
    }

    public abstract float getWidth();
    public abstract float getHeight();
}
