package com.kaliwe.neercgame.box2d;

/**
 * Created by anton on 30.11.15.
 */
public class DisappearUserData extends UserData {
    private final String name;

    public DisappearUserData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
