package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.kaliwe.neercgame.actors.Pig;
import com.kaliwe.neercgame.actors.Stanok;
import com.kaliwe.neercgame.utils.Background;
import com.kaliwe.neercgame.utils.ResourceUtils;
import com.kaliwe.neercgame.utils.WorldUtils;

/**
 * Created by anton on 04.12.15.
 */
public class Level3 extends Level1 {
    public Level3() {
        super("level3", "Level with STANOK");
        Gdx.gl.glClearColor(135/255f,206/255f,235/255f,1);

        float skyOffset = 260;
        bgs.add(new Background(ResourceUtils.getTextureRegion("cloudsBack"), 0.4f, 5f, 0.9f, hudCam, 0, skyOffset));
        bgs.add(new Background(ResourceUtils.getTextureRegion("clouds"), 0.5f, 8f, 0.8f, hudCam, 0, skyOffset));

        bgs.add(new Background(ResourceUtils.getTextureRegion("buildings"), 1.5f, 10f , 1f, hudCam, -1000, cameraLowerY*14));
        cameraLowerY = 7;
        collectSmt = ResourceUtils.getSound("oink");
    }

    @Override
    public void setupPlayer() {
        player = new Stanok(WorldUtils.createPlayer(world, mapHolder.map, mapHolder));
        addActor(player);
    }

    @Override
    public void setupBugs() {
        maxScore = 0;
        for (Body b : WorldUtils.createBugs(world, mapHolder.map)) {
            addActor(new Pig(b));
            maxScore++;
        }
    }
}
