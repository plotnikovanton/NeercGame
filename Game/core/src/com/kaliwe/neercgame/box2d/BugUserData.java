package com.kaliwe.neercgame.box2d;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kaliwe.neercgame.enums.UserDataType;

/**
 * Created by anton on 01.12.15.
 */
public class BugUserData extends UserData {
    public Actor actor;
    public short numberOfContacts;

    public BugUserData() {
        userDataType = UserDataType.BUG;
    }
}
