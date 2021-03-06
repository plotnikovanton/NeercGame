package com.kaliwe.neercgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
    private static Map<String, Sound> sounds = new HashMap<>();

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

        // Player
        textureBuffer = new Texture(Gdx.files.internal("player-emo.png"));
        split = TextureRegion.split(textureBuffer, 19, 30);
        mirror = flip(split);

        animations.put("standRightEmo", new Animation(10f, mirror[0][0]));
        animations.put("standLeftEmo", new Animation(10f, split[0][0]));

        animations.put("walkRightEmo", new Animation(0.10f, Arrays.copyOfRange(mirror[0], 1, 5)));
        animations.put("walkLeftEmo", new Animation(0.10f, Arrays.copyOfRange(split[0], 1, 5)));

        animations.put("jumpUpLeftEmo", new Animation(10f, split[1][0]));
        animations.put("jumpUpRightEmo", new Animation(10f, mirror[1][0]));

        animations.put("jumpDownRightEmo", new Animation(10f, mirror[1][1]));
        animations.put("jumpDownLeftEmo", new Animation(10f, split[1][1]));

        animations.put("burningRightEmo", new Animation(0.10f, Arrays.copyOfRange(mirror[2], 0, 2)));
        animations.put("burningLeftEmo", new Animation(0.10f, Arrays.copyOfRange(split[2], 0, 2)));

        animations.put("deadRightEmo", new Animation(0.10f, Arrays.copyOfRange(mirror[3], 0, 2)));
        animations.put("deadLeftEmo", new Animation(0.10f, Arrays.copyOfRange(split[3], 0, 2)));

        // Stanok
        textureBuffer = new Texture(Gdx.files.internal("stankevich.png"));
        split = TextureRegion.split(textureBuffer, 19, 30);
        mirror = flip(split);

        animations.put("standRightSt", new Animation(10f, mirror[0][0]));
        animations.put("standLeftSt", new Animation(10f, split[0][0]));

        animations.put("walkRightSt", new Animation(0.10f, Arrays.copyOfRange(mirror[0], 1, 5)));
        animations.put("walkLeftSt", new Animation(0.10f, Arrays.copyOfRange(split[0], 1, 5)));

        animations.put("jumpUpLeftSt", new Animation(10f, split[1][0]));
        animations.put("jumpUpRightSt", new Animation(10f, mirror[1][0]));

        animations.put("jumpDownRightSt", new Animation(10f, mirror[1][1]));
        animations.put("jumpDownLeftSt", new Animation(10f, split[1][1]));

        animations.put("deadRightSt", new Animation(0.10f, Arrays.copyOfRange(mirror[2], 0, 2)));
        animations.put("deadLeftSt", new Animation(0.10f, Arrays.copyOfRange(split[2], 0, 2)));

        // Egor
        textureBuffer = new Texture(Gdx.files.internal("egor.png"));
        split = TextureRegion.split(textureBuffer, 23, 46);
        mirror = flip(split);

        animations.put("egorLeft", new Animation(0.1f, Arrays.copyOfRange(mirror[0], 0, 4)));
        animations.put("egorRight", new Animation(0.1f, Arrays.copyOfRange(split[0], 0, 4)));


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
        textureRegions.put("special-edition", new TextureRegion(new Texture(Gdx.files.internal("special-edition.png"))));
        textureRegions.put("buildings-zinger", new TextureRegion(new Texture(Gdx.files.internal("buildings-zinger.png"))));
        textureRegions.put("clouds", new TextureRegion(new Texture(Gdx.files.internal("clouds.png"))));
        textureRegions.put("x13", new TextureRegion(new Texture(Gdx.files.internal("x13.png"))));
        textureRegions.put("cloudsBack", new TextureRegion(new Texture(Gdx.files.internal("clouds_back.png"))));

        // Photos

        textureRegions.put("level0", new TextureRegion(new Texture(Gdx.files.internal("photos/level0.png"))));
        textureRegions.put("level1", new TextureRegion(new Texture(Gdx.files.internal("photos/level1.png"))));
        textureRegions.put("level2", new TextureRegion(new Texture(Gdx.files.internal("photos/level2.png"))));
        textureRegions.put("level3", new TextureRegion(new Texture(Gdx.files.internal("photos/level3.png"))));
        textureRegions.put("level4", new TextureRegion(new Texture(Gdx.files.internal("photos/level4.png"))));
        textureRegions.put("level5", new TextureRegion(new Texture(Gdx.files.internal("photos/level5.png"))));
        textureRegions.put("level6", new TextureRegion(new Texture(Gdx.files.internal("photos/level6.png"))));
        textureRegions.put("level7", new TextureRegion(new Texture(Gdx.files.internal("photos/level7.png"))));
        textureRegions.put("level8", new TextureRegion(new Texture(Gdx.files.internal("photos/level8.png"))));
        textureRegions.put("level9", new TextureRegion(new Texture(Gdx.files.internal("photos/level9.png"))));
        textureRegions.put("level10", new TextureRegion(new Texture(Gdx.files.internal("photos/level10.png"))));
        textureRegions.put("level11", new TextureRegion(new Texture(Gdx.files.internal("photos/level11.png"))));

        // Sound
        sounds.put("jump", Gdx.audio.newSound(Gdx.files.internal("music/jump.ogg")));
        sounds.put("steps", Gdx.audio.newSound(Gdx.files.internal("music/footsteps.ogg")));
        sounds.put("fail", Gdx.audio.newSound(Gdx.files.internal("music/fail.ogg")));
        //sounds.put("hallOfFame", Gdx.audio.newSound(Gdx.files.internal("music/hall_of_fame.ogg")));
        sounds.put("getCoin", Gdx.audio.newSound(Gdx.files.internal("music/coin-get.ogg")));
        sounds.put("main", Gdx.audio.newSound(Gdx.files.internal("music/main1.ogg")));
        sounds.put("meow", Gdx.audio.newSound(Gdx.files.internal("music/meow.ogg")));
        sounds.put("oink", Gdx.audio.newSound(Gdx.files.internal("music/oink.ogg")));

        // Map
        maps.put("levelBetweenLevels", mapLoader.load(Gdx.files.internal("levelBetweenLevels.tmx").path()));
        maps.put("level0", mapLoader.load(Gdx.files.internal("level0.tmx").path()));
        maps.put("level1", mapLoader.load(Gdx.files.internal("level1.tmx").path()));
        maps.put("level2", mapLoader.load(Gdx.files.internal("level2.tmx").path()));
        maps.put("level3", mapLoader.load(Gdx.files.internal("level3.tmx").path()));

        maps.put("level4", mapLoader.load(Gdx.files.internal("level4.tmx").path()));
        maps.put("level5", mapLoader.load(Gdx.files.internal("level5.tmx").path()));
        maps.put("level6", mapLoader.load(Gdx.files.internal("level6.tmx").path()));
        maps.put("level7", mapLoader.load(Gdx.files.internal("level7.tmx").path()));

        maps.put("level8", mapLoader.load(Gdx.files.internal("level8.tmx").path()));
        maps.put("level9", mapLoader.load(Gdx.files.internal("level9.tmx").path()));
        maps.put("level10", mapLoader.load(Gdx.files.internal("level10.tmx").path()));
        maps.put("level11", mapLoader.load(Gdx.files.internal("level11.tmx").path()));

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

    public static Sound getSound (String key) {
        return getIfExists(sounds, key);
    }
}