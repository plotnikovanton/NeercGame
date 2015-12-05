package com.kaliwe.neercgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anton on 03.12.15.
 */
public class ResourceUtils {
    private static Map<String, Animation> animations = new HashMap<>();
    private static Map<String, BitmapFont> fonts = new HashMap<>();
    private static Map<String, TextureRegion> textureRegions = new HashMap<>();
    private static Map<String, TiledMap> maps = new HashMap<>();

    static {
        Texture textureBuffer;
        TextureRegion textureRegionBuffer;
        TextureRegion split[][];
        TextureRegion mirror[][];
        TmxMapLoader mapLoader = new TmxMapLoader();

        // Player
        textureBuffer = new Texture(Gdx.files.internal("player.png"));
        split = TextureRegion.split(textureBuffer, 19, 30);
        mirror = flip(split);

        animations.put("standRight", new Animation(10f, mirror[0][0]));
        animations.put("standLeft", new Animation(10f, split[0][0]));

        animations.put("walkRight", new Animation(0.10f, Arrays.copyOfRange(mirror[0], 1, 5)));
        animations.put("walkLeft", new Animation(0.10f, Arrays.copyOfRange(split[0], 1, 5)));

        animations.put("jumpUpLeft", new Animation(10f, split[1][0]));
        animations.put("jumpUpRight", new Animation(10f, mirror[1][0]));

        animations.put("jumpDownRight", new Animation(10f, mirror[1][1]));
        animations.put("jumpDownLeft", new Animation(10f, split[1][1]));

        animations.put("burningRight", new Animation(0.10f, Arrays.copyOfRange(mirror[2], 0, 2)));
        animations.put("burningLeft", new Animation(0.10f, Arrays.copyOfRange(split[2], 0, 2)));

        animations.put("deadRight", new Animation(0.10f, Arrays.copyOfRange(mirror[3], 0, 2)));
        animations.put("deadLeft", new Animation(0.10f, Arrays.copyOfRange(split[3], 0, 2)));

        // Stanok
        textureBuffer = new Texture(Gdx.files.internal("stankevich.png"));
        split = TextureRegion.split(textureBuffer, 18, 30);
        mirror = flip(split);

        animations.put("standRightSt", new Animation(10f, mirror[0][0]));
        animations.put("standLeftSt", new Animation(10f, split[0][0]));

        animations.put("walkRightSt", new Animation(0.10f, Arrays.copyOfRange(mirror[0], 1, 5)));
        animations.put("walkLeftSt", new Animation(0.10f, Arrays.copyOfRange(split[0], 1, 5)));

        animations.put("jumpUpLeftSt", new Animation(10f, split[1][0]));
        animations.put("jumpUpRightSt", new Animation(10f, mirror[1][0]));

        animations.put("jumpDownRightSt", new Animation(10f, mirror[0][3]));
        animations.put("jumpDownLeftSt", new Animation(10f, split[0][3]));

        animations.put("deadRightSt", new Animation(0.10f, Arrays.copyOfRange(mirror[0], 0, 2)));
        animations.put("deadLeftSt", new Animation(0.10f, Arrays.copyOfRange(split[0], 0, 2)));

        // Cat
        textureBuffer = new Texture(Gdx.files.internal("cat.png"));
        split = TextureRegion.split(textureBuffer, 26, 22);
        mirror = flip(split);

        animations.put("catWalkRight", new Animation(0.1f, split[2]));
        animations.put("catWalkLeft", new Animation(0.1f, mirror[2]));

        animations.put("catSitsRight", new Animation(0.05f, split[0]));
        animations.put("catSitsLeft", new Animation(0.05f, mirror[0]));

        animations.put("catTailRight", new Animation(0.1f, split[1]));
        animations.put("catTailLeft", new Animation(0.1f, mirror[1]));

        // Rain
        textureBuffer = new Texture(Gdx.files.internal("cloud-rain.png"));
        split = TextureRegion.split(textureBuffer, 58, 19);
        textureRegions.put("rainyCloud", split[0][0]);
        textureRegions.put("rain", split[0][1]);

        // Bug
        textureBuffer = new Texture(Gdx.files.internal("bug.png"));
        split = TextureRegion.split(textureBuffer, 20, 20);
        animations.put("bug0", new Animation(0.06f, split[0]));
        animations.put("bug1", new Animation(0.06f, split[1]));
        textureRegions.put("bugHud", split[0][0]);

        // Pig
        textureBuffer = new Texture(Gdx.files.internal("pig.png"));
        split = TextureRegion.split(textureBuffer, 20, 20);
        textureRegions.put("pigNormal1", split[0][0]);
        textureRegions.put("pigNormal2", split[0][1]);
        animations.put("pigSpecial", new Animation(0.1f, Arrays.copyOfRange(split[0], 2,4)));

        // Fonts
        fonts.put("visitor", new BitmapFont(Gdx.files.internal("visitor.fnt"), Gdx.files.internal("visitor.png"), false));

        // Backgrounds
        textureRegions.put("buildings", new TextureRegion(new Texture(Gdx.files.internal("buildings.png"))));
        textureRegions.put("clouds", new TextureRegion(new Texture(Gdx.files.internal("clouds.png"))));
        textureRegions.put("cloudsBack", new TextureRegion(new Texture(Gdx.files.internal("clouds_back.png"))));

        // Map
        maps.put("levelBetweenLevels", mapLoader.load(Gdx.files.internal("levelBetweenLevels.tmx").path()));
        maps.put("level0", mapLoader.load(Gdx.files.internal("level0.tmx").path()));
        maps.put("level1", mapLoader.load(Gdx.files.internal("level1.tmx").path()));
        maps.put("level2", mapLoader.load(Gdx.files.internal("level2.tmx").path()));
        maps.put("level3", mapLoader.load(Gdx.files.internal("level3.tmx").path()));
        maps.put("level6", mapLoader.load(Gdx.files.internal("level6.tmx").path()));

        maps.put("test", mapLoader.load(Gdx.files.internal("test.tmx").path()));

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

    private static <T> T getIfExists(Map<String, T> map, String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            throw new IllegalArgumentException("Have not got item called: " + key);
        }
    }

    public static Animation getAnimation(String key) {
        return getIfExists(animations, key);
    }

    public static BitmapFont getFont(String key) {
        return getIfExists(fonts, key);
    }

    public static TextureRegion getTextureRegion(String key) {
        return getIfExists(textureRegions, key);
    }

    public static TiledMap getMap(String key) {
        return getIfExists(maps, key);
    }
}