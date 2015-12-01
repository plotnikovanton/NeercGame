package com.kaliwe.neercgame.box2d;

import com.kaliwe.neercgame.actors.DisappearObject;
import com.kaliwe.neercgame.enums.UserDataType;

/**
 * Created by anton on 30.11.15.
 */
public class DisappearUserData extends UserData {
    private final String name;
    private float height;
    private float width;
    private DisappearObject actor;

    public DisappearUserData(String name, float height, float width) {
        this.name = name;
        this.height = height;
        this.width = width;

        this.userDataType = UserDataType.DISSAPEAR;
    }

    public String getName() {
        return name;
    }

    public DisappearObject getActor() {
        return actor;
    }

    public void setActor(DisappearObject actor) {
        this.actor = actor;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }
}
