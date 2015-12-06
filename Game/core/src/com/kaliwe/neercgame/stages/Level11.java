package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.Gdx;
import com.kaliwe.neercgame.actors.Stanok;
import com.kaliwe.neercgame.utils.Background;
import com.kaliwe.neercgame.utils.ResourceUtils;
import com.kaliwe.neercgame.utils.WorldUtils;

/**
 * Created by anton on 05.12.15.
 */
public class Level11 extends Level1 {
    public Level11() {
        super("level11", "level11");
        Gdx.gl.glClearColor(135/255f,206/255f,235/255f,1);
        cameraLowerY = 10;
        float skyOffset = 260;
        bgs.add(new Background(ResourceUtils.getTextureRegion("cloudsBack"), 0.4f, 5f, 0.9f, hudCam, 0, skyOffset));
        bgs.add(new Background(ResourceUtils.getTextureRegion("clouds"), 0.5f, 8f, 0.8f, hudCam, 0, skyOffset));
        bgs.add(new Background(ResourceUtils.getTextureRegion("buildings"), 1.5f, 10f , 1f, hudCam, 400, cameraLowerY*14+95));

    }

    @Override
    public void setupPlayer() {
        player = new Stanok(WorldUtils.createPlayer(world, mapHolder.map, mapHolder));
        addActor(player);
    }
}
