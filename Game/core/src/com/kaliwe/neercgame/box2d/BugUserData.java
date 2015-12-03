package com.kaliwe.neercgame.box2d;

import com.kaliwe.neercgame.actors.Bug;
import com.kaliwe.neercgame.enums.UserDataType;

/**
 * Created by anton on 01.12.15.
 */
public class BugUserData extends UserData {
    public Bug actor;
    public short numberOfContacts;

    public BugUserData() {
        userDataType = UserDataType.BUG;
    }
}
