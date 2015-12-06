package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.kaliwe.neercgame.utils.Background;
import com.kaliwe.neercgame.utils.ResourceUtils;

/**
 * Created by anton on 06.12.15.
 */
public class Level9 extends Level1 {
    public Level9() {
        super("level9", "Reversed shit");
        Gdx.gl.glClearColor(135/255f,206/255f,235/255f,1);
        cameraLowerY = 15;
        float skyOffset = 230;
        hudCam.rotate(180f);
        hudCam.update();
        bgs.add(new Background(ResourceUtils.getTextureRegion("cloudsBack"), 0.4f, 5f, 0.9f, hudCam, 0, skyOffset));
        bgs.add(new Background(ResourceUtils.getTextureRegion("clouds"), 0.5f, 8f, 0.8f, hudCam, 0, skyOffset));
        bgs.add(new Background(ResourceUtils.getTextureRegion("special-edition"), 0.5f, 10f, 0.8f, hudCam, 0, skyOffset+5));
        bgs.add(new Background(ResourceUtils.getTextureRegion("buildings"), 1.5f, 10f , 1f, hudCam, 0, cameraLowerY*14));

        ((OrthographicCamera)getCamera()).rotate(180f);

    }

}
