package com.kaliwe.neercgame.box2d;

import com.kaliwe.neercgame.actors.Cat;
import com.kaliwe.neercgame.enums.UserDataType;

/**
 * Created by anton on 05.12.15.
 */
public class CatUserData extends UserData {
    public Cat cat;
    public final float a;
    public final float b;

    public CatUserData(float a, float b) {
        userDataType = UserDataType.CAT;
        this.a = a;
        this.b = b;
    }
}
