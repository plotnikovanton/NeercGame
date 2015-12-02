package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.kaliwe.neercgame.box2d.PlayerUserData;
import com.kaliwe.neercgame.enums.PlayerState;
import com.kaliwe.neercgame.utils.Constants;
import com.kaliwe.neercgame.utils.ResourceUtils;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by anton on 18.11.15.
 */
public class Player extends GameActor {
    private int numOfFootContacts;
    private boolean dodging;

    private PlayerState state;
    private PlayerState animationState;
    private boolean turnRight;

    private java.util.Map<PlayerState, Animation> rightAnimations = new HashMap<>();
    private java.util.Map<PlayerState, Animation> leftAnimations = new HashMap<>();
    private Animation animation;

    private float stateTime;

    public Player(Body body) {
        super(body);

        Arrays.stream(new String[]{
                "standLeft", "walkLeft", "jumpUpLeft", "jumpDownLeft", "burningLeft", "deadLeft"
        }).forEach(x -> leftAnimations.put(PlayerState.fromString(x), ResourceUtils.getAnimation(x)));

        Arrays.stream(new String[]{
                "standRight", "walkRight", "jumpUpRight", "jumpDownRight", "burningRight", "deadRight"
        }).forEach(x -> rightAnimations.put(PlayerState.fromString(x), ResourceUtils.getAnimation(x)));

        turnRight = true;
        animationState = PlayerState.JUMP;
        setState(PlayerState.STAND);

        stateTime = 0f;
        numOfFootContacts = 0;
    }

    @Override
    public PlayerUserData getUserData() {
        return (PlayerUserData) userData;
    }

    public void jump() {
        if (isOnGround()) {
            body.applyLinearImpulse(getUserData().getJumpingLinearImpulse(), body.getWorldCenter(), true);
        }
    }

    public void jumpOutOfEnemy () {
        Vector2 linearVelocity = body.getLinearVelocity();
        linearVelocity.y=0;
        body.setLinearVelocity(linearVelocity);
        body.applyLinearImpulse(getUserData().getJumpingOutEnemyLinearImpulse(), body.getWorldCenter(), true);
        choiceAnimation();
    }

    public void decNumOfFootContacts() {
        numOfFootContacts--;
        choiceAnimation();
    }

    public void incNumOfFootContacts() {
        numOfFootContacts++;
        choiceAnimation();
    }

    public void setState(PlayerState state) {
        if (this.state != state) {
            if (!(this.state == PlayerState.DEAD || this.state == PlayerState.BURNING)) {
                this.state = state;
            }
            animationState = state;
            choiceAnimation();
        }
    }

    public void setTurnRight(boolean value) {
        this.turnRight = value;
        choiceAnimation();
    }

    public void setLinearVelocity(float x, float y) {
        body.setLinearVelocity(x, y);
    }

    private void choiceAnimation() {
        if(!isOnGround() && state != PlayerState.BURNING && state != PlayerState.DEAD) {
            if (body.getLinearVelocity().y > 0) {
                animationState = PlayerState.JUMP_UP;
            } else {
                animationState = PlayerState.JUMP_DOWN;
            }
        } else {
            animationState = state;
        }
        if(turnRight) {
            animation = rightAnimations.get(animationState);
        } else {
            animation = leftAnimations.get(animationState);
        }
    }
    
    public boolean isOnGround() {
        return numOfFootContacts > 0;
    }

    @Override
    public void act(float delta) {
        if (animationState == PlayerState.JUMP_UP) {
            choiceAnimation();
        }
        if (state == PlayerState.WALK) {
            if (turnRight) {
                if (body.getLinearVelocity().x < Constants.PLAYER_MAX_SPEED) {
                    body.applyLinearImpulse(new Vector2(Constants.PLAYER_SPEED, 0), body.getLocalCenter(), true);
                }
            } else {
                if (body.getLinearVelocity().x > -Constants.PLAYER_MAX_SPEED) {
                    body.applyLinearImpulse(new Vector2(-Constants.PLAYER_SPEED, 0), body.getLocalCenter(), true);
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = animation.getKeyFrame(stateTime, true);
        float textureHeight = getHeight() * 1.15f;
        float textureWidth = frame.getRegionWidth() * textureHeight / frame.getRegionHeight();
        batch.draw(frame,
                   body.getPosition().x - textureWidth / 2,
                   getOffsetY(),
                   textureWidth,
                   textureHeight);
    }

    public Vector2 getPosition() {
        return super.body.getPosition();
    }

    public boolean isDead() {
        return state == PlayerState.DEAD || state == PlayerState.BURNING;
    }
}
