package com.kaliwe.neercgame.box2d;

import com.kaliwe.neercgame.enums.UserDataType;

/**
 * Created by anton on 06.12.15.
 */
public class EgorUserData extends UserData {
    public final float a;
    public final float b;

    public EgorUserData(float a, float b) {
        userDataType = UserDataType.EGOR;
        this.a = a;
        this.b = b;
    }
}
