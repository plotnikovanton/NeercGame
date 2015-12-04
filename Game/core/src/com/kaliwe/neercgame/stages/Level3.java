package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.physics.box2d.Body;
import com.kaliwe.neercgame.actors.Pig;
import com.kaliwe.neercgame.actors.Stanok;
import com.kaliwe.neercgame.utils.WorldUtils;

/**
 * Created by anton on 04.12.15.
 */
public class Level3 extends Level1 {
    public Level3() {
        super("level3", "Level with STANOK");
        cameraLowerY = -50;
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
