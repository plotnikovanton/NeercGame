package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.kaliwe.neercgame.enums.PlayerState;
import com.kaliwe.neercgame.utils.ResourceUtils;

import java.util.Arrays;

/**
 * Created by anton on 06.12.15.
 */
public class Emo extends Player {
    public Emo(Body body) {
        super(body);

        Arrays.stream(new String[] {"stand", "walk", "jumpUp", "jumpDown", "dead"}).forEach(x -> {
            leftAnimations.put(PlayerState.fromString(x + "Left"), ResourceUtils.getAnimation(x + "LeftEmo"));
            rightAnimations.put(PlayerState.fromString(x + "Right"), ResourceUtils.getAnimation(x + "RightEmo"));
        });
    }
}
