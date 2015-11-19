package com.kaliwe.neercgame.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by anton on 18.11.15.
 */
public class Constants {
    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 480;

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);
    public static final int PPM = 10;

    public static final float PLAYER_X = 2;
    public static final float PLAYER_Y = 5;
    public static final float PLAYER_WIDTH = 1f;
    public static final float PLAYER_HEIGHT = 2f;
    public static final Vector2 PLAYER_JUMPING_LINEAR_IMPULSE = new Vector2(0, 13f);
    public static final float PLAYER_GRAVITY_SCALE = 3f;
    public static float PLAYER_DENSITY = 0.5f;
    public static final float PLAYER_DODGE_X = 2f;
    public static final float PLAYER_DODGE_Y = 1.5f;
    public static final float PLAYER_SPEED = 20f;
}
