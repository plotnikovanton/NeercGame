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
}
