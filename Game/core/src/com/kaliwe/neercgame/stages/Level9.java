package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by anton on 06.12.15.
 */
public class Level9 extends Level1 {
    public Level9() {
        super("level9", "Reversed shit");
        ((OrthographicCamera)getCamera()).rotate(180f);
    }

}
