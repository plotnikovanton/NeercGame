package com.kaliwe.neercgame.enums;

/**
 * Created by anton on 20.11.15.
 */
public enum PlayerState {
    WALK,
    JUMP,
    STAND,
    BURNING,
    JUMP_UP, JUMP_DOWN, DEAD;

    public static PlayerState fromString(String key) {
        switch (key){
            case "walkRight":
            case "walkLeft":
                return WALK;
            case "jumpUpRight":
            case "jumpUpLeft":
                return JUMP_UP;
            case "standRight":
            case "standLeft":
                return STAND;
            case "burningLeft":
            case "burningRight":
                return BURNING;
            case "jumpDownRight":
            case "jumpDownLeft":
                return JUMP_DOWN;
            case "deadRight":
            case "deadLeft":
                return DEAD;
            default:
                throw new IllegalArgumentException("What the fuck man, I can't hold it: " + key);
        }
    }
}
