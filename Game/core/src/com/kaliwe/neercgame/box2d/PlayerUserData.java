package com.kaliwe.neercgame.box2d;

import com.badlogic.gdx.math.Vector2;
import com.kaliwe.neercgame.enums.UserDataType;
import com.kaliwe.neercgame.utils.Constants;

/**
 * Created by anton on 18.11.15.
 */
public class PlayerUserData extends UserData {
    private Vector2 jumpingLinearImpulse;
    private Vector2 jumpingOutEnemyLinearImpulse;
    private final Vector2 runningPosition = new Vector2(Constants.PLAYER_X, Constants.PLAYER_Y);
    private final Vector2 dodgePosition = new Vector2(Constants.PLAYER_DODGE_X, Constants.PLAYER_DODGE_Y);

    public PlayerUserData() {
        super();
        jumpingLinearImpulse = Constants.PLAYER_JUMPING_LINEAR_IMPULSE;
        jumpingOutEnemyLinearImpulse = Constants.PLAYER_JUMPING_OUT_ENEMY_LINEAR_IMPULSE;
        userDataType = UserDataType.PLAYER;
    }

    @Override
    public float getWidth() {
        return Constants.PLAYER_WIDTH;
    }

    @Override
    public float getHeight() {
        return Constants.PLAYER_HEIGHT + Constants.FOOT_HEIGHT;
    }


    public Vector2 getJumpingLinearImpulse() {
        return jumpingLinearImpulse;
    }

    public void setJumpingLinearImpulse(Vector2 jumpingLinearImpulse) {
        this.jumpingLinearImpulse = jumpingLinearImpulse;
    }

    public float getDodgeAngle() {
        // In radians
        return (float) (-90f * (Math.PI / 180f));
    }

    public Vector2 getRunningPosition() {
        return runningPosition;
    }

    public Vector2 getDodgePosition() {
        return dodgePosition;
    }

    public Vector2 getJumpingOutEnemyLinearImpulse() {
        return jumpingOutEnemyLinearImpulse;
    }
}
