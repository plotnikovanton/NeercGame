package com.kaliwe.neercgame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anton on 03.12.15.
 */
public class ResourceUtils {
    private static Map<String, Animation> animations = new HashMap<>();

    static {
        Texture textureBuffer;
        TextureRegion textureRegionBuffer;
        TextureRegion split[][];
        TextureRegion mirror[][];

        // Player animations
        textureBuffer = new Texture("player.png");
        split = TextureRegion.split(textureBuffer, 19, 30);
        mirror = flip(split);

        animations.put("standRight", new Animation(10f, mirror[0][0]));
        animations.put("standLeft", new Animation(10f, split[0][0]));

        animations.put("walkRight", new Animation(0.10f, Arrays.copyOfRange(mirror[0], 1, 4)));
        animations.put("walkLeft", new Animation(0.10f, Arrays.copyOfRange(split[0], 1, 4)));

        animations.put("jumpUpLeft", new Animation(10f, split[1][0]));
        animations.put("jumpUpRight", new Animation(10f, mirror[1][0]));

        animations.put("jumpDownRight", new Animation(10f, mirror[1][1]));
        animations.put("jumpDownLeft", new Animation(10f, split[1][1]));

        animations.put("burningRight", new Animation(0.10f, Arrays.copyOfRange(mirror[2], 0, 2)));
        animations.put("burningLeft", new Animation(0.10f, Arrays.copyOfRange(split[2], 0, 2)));

        animations.put("deadRight", new Animation(0.10f, Arrays.copyOfRange(mirror[3], 0, 2)));
        animations.put("deadLeft", new Animation(0.10f, Arrays.copyOfRange(split[3], 0, 2)));

    }

    private static TextureRegion[][] flip(TextureRegion[][] split) {
        TextureRegion mirror[][] = new TextureRegion[split.length][split[0].length];
        for (int i = 0; i < split.length; i++) {
            for (int j = 0; j < split[0].length; j++) {
                TextureRegion tmp = new TextureRegion(split[i][j]);
                tmp.flip(true, false);
                mirror[i][j] = tmp;
            }
        }
        return mirror;
    }

    public static Animation getAnimation(String key) {
        if (animations.containsKey(key)) {
            return animations.get(key);
        } else {
            throw new IllegalArgumentException("Have not got Animation called: " + key);
        }
    }
}