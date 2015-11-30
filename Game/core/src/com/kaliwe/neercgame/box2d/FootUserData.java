package com.kaliwe.neercgame.box2d;

import com.kaliwe.neercgame.enums.UserDataType;
import com.kaliwe.neercgame.utils.Constants;

/**
 * Created by anton on 18.11.15.
 */
public class FootUserData extends UserData {
    public FootUserData() {
        super();
        userDataType = UserDataType.FOOT;
    }

    @Override
    public float getWidth() {
        return Constants.PLAYER_WIDTH-0.04f;
    }

    @Override
    public float getHeight() {
        return 0;
    }
}
