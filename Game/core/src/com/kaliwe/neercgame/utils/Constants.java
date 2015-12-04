package com.kaliwe.neercgame.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by anton on 18.11.15.
 */
public class Constants {
    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 480;

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);
    public static final float WORLD_FRICTION = 5f;
    public static int PPM = 12;

    public static final float PLAYER_X = 2;
    public static final float PLAYER_Y = 5;
    public static final float PLAYER_WIDTH = 0.7f;
    public static final float PLAYER_HEIGHT = 1.5f;
    public static final Vector2 PLAYER_JUMPING_LINEAR_IMPULSE = new Vector2(0, 13f);
    public static final Vector2 PLAYER_JUMPING_OUT_ENEMY_LINEAR_IMPULSE = new Vector2(0, 15f);
    public static final float PLAYER_GRAVITY_SCALE = 10f;
    public static final float PLAYER_DENSITY = 0.5f;
    public static final float PLAYER_DODGE_X = 2f;
    public static final float PLAYER_DODGE_Y = 1.5f;
    public static final float PLAYER_SPEED = 1.6f;
    public static final float PLAYER_MAX_SPEED = 8f;

    public static final float FOOT_HEIGHT = 0.02f;
    public static final float SIMPLE_ENEMY_SPEED = 5f;
    public static final float DISAPPEAR_OBJECT_WIDTH = 6f;
    public static final float DISAPPEAR_OBJECT_HEIGHT = 4f;
}
