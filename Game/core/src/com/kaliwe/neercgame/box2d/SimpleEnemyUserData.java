package com.kaliwe.neercgame.box2d;

import com.kaliwe.neercgame.enums.UserDataType;

/**
 * Created by anton on 20.11.15.
 */
public class SimpleEnemyUserData extends UserData {
    private float height;
    private float width;
    private final float[] track;

    public SimpleEnemyUserData(float[] track){
        userDataType = UserDataType.SIMPLE_ENEMY;
        this.height = 1f;
        this.width = 1f;
        this.track = track;
    }

    public float[] getTrack() {
        return track;
    }
}
