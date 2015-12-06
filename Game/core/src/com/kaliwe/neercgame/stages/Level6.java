package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.Gdx;
import com.kaliwe.neercgame.utils.Background;
import com.kaliwe.neercgame.utils.ResourceUtils;

/**
 * Created by anton on 04.12.15.
 */
public class Level6 extends Level1 {
    public Level6() {
        super("level6", "level6");
        Gdx.gl.glClearColor(135/255f,206/255f,235/255f,1);
        float skyOffset = 260;
        bgs.add(new Background(ResourceUtils.getTextureRegion("cloudsBack"), 0.4f, 5f, 0.9f, hudCam, 0, skyOffset));
        bgs.add(new Background(ResourceUtils.getTextureRegion("clouds"), 0.5f, 8f, 0.8f, hudCam, 0, skyOffset));


    }
}
