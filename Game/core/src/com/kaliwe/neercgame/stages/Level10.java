package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.Gdx;
import com.kaliwe.neercgame.utils.Background;
import com.kaliwe.neercgame.utils.ResourceUtils;

/**
 * Created by anton on 05.12.15.
 */
public class Level10 extends Level1 {
    public Level10() {
        super("level10", "x13");
        Gdx.gl.glClearColor(135/255f,206/255f,235/255f,1);

        float skyOffset = 260;
        bgs.add(new Background(ResourceUtils.getTextureRegion("cloudsBack"), 0.4f, 5f, 0.9f, hudCam, 0, skyOffset));
        bgs.add(new Background(ResourceUtils.getTextureRegion("clouds"), 0.5f, 8f, 0.8f, hudCam, 0, skyOffset));
        bgs.add(new Background(ResourceUtils.getTextureRegion("x13"), 0.5f, 10f, 0.8f, hudCam, 0, skyOffset-40));
        bgs.add(new Background(ResourceUtils.getTextureRegion("buildings"), 1.5f, 10f , 1f, hudCam, 400, cameraLowerY*14));

    }
}
