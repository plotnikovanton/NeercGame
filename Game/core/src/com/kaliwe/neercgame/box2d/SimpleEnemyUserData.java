package com.kaliwe.neercgame.box2d;

import com.badlogic.gdx.physics.box2d.ChainShape;
import com.kaliwe.neercgame.enums.UserDataType;

/**
 * Created by anton on 20.11.15.
 */
public class SimpleEnemyUserData extends UserData {
    private float height;
    private float width;
    private ChainShape chain;

    public SimpleEnemyUserData(ChainShape chain){
        userDataType = UserDataType.SIMPLE_ENEMY;
        this.height = 1f;
        this.width = 1f;
        this.chain = chain;
    }

    public float getHeight() {
        return height;
    }

    public ChainShape getChain() {
        return chain;
    }

    public float getWidth() {
        return width;
    }
}
