package com.kaliwe.neercgame.box2d;

import com.kaliwe.neercgame.enums.UserDataType;

/**
 * Created by anton on 18.11.15.
 */
public abstract class UserData {
    protected UserDataType userDataType;
    public boolean isFlaggedForDelete = false;

    public UserData() {
    }

    public UserDataType getUserDataType() {
        return userDataType;
    }
}
