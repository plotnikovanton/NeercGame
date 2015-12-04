package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.kaliwe.neercgame.utils.Background;
import com.kaliwe.neercgame.utils.ResourceUtils;

/**
 * Created by anton on 03.12.15.
 */
public class Level2 extends Level1 {
    public Level2() {
        super("level2", "level 3");
        Gdx.gl.glClearColor(90/255f, 90/255f, 90/255f, 1);
        bgs.add(new Background(ResourceUtils.getTextureRegion("buildings"), 1.5f, 10f , 1f, hudCam, -1000, cameraLowerY*14));
        tint = new Color(Color.GRAY);
    }
}
