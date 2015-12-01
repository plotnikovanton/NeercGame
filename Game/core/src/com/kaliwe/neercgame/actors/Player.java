package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.kaliwe.neercgame.box2d.PlayerUserData;
import com.kaliwe.neercgame.enums.PlayerState;
import com.kaliwe.neercgame.utils.Constants;

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

    private java.util.Map<PlayerState, Animation> rigthAnimations = new HashMap<>();
    private java.util.Map<PlayerState, Animation> leftAnimations = new HashMap<>();
    private Animation animation;

    private float stateTime;

    public Player(Body body) {
        super(body);
        // TODO: move to TextureUtil
        Texture texture = new Texture("player.png");
        TextureRegion split[][] = TextureRegion.split(texture, 19, 30);
        TextureRegion mirror[][] = TextureRegion.split(texture, 19, 30);
        for (TextureRegion[] i : mirror) {
            for (TextureRegion j : i) {
                j.flip(true, false);
            }
        }

        rigthAnimations.put(PlayerState.STAND, new Animation(10f, mirror[0][0]));
        leftAnimations.put(PlayerState.STAND, new Animation(10f, split[0][0]));

        rigthAnimations.put(PlayerState.WALK, new Animation(0.10f, Arrays.copyOfRange(mirror[0], 1, 4)));
        leftAnimations.put(PlayerState.WALK, new Animation(0.10f, Arrays.copyOfRange(split[0], 1, 4)));

        rigthAnimations.put(PlayerState.JUMP, new Animation(10f, mirror[1][0]));
        leftAnimations.put(PlayerState.JUMP, new Animation(10f, split[1][0]));

        rigthAnimations.put(PlayerState.BURNING, new Animation(0.10f, Arrays.copyOfRange(mirror[2], 0, 2)));
        leftAnimations.put(PlayerState.BURNING, new Animation(0.10f, Arrays.copyOfRange(split[2], 0, 2)));

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
    }

    public void decNumOfFootContacts() {
        numOfFootContacts--;
        choiseAnimation();
    }

    public void incNumOfFootContacts() {
        numOfFootContacts++;
        choiseAnimation();
    }

    public void setState(PlayerState state) {
        if (this.state != state) {
            if (!(this.state == PlayerState.DEAD || this.state == PlayerState.BURNING)) {
                this.state = state;
            }
            animationState = isOnGround() ? PlayerState.JUMP : state;
            choiseAnimation();
        }
    }

    public void setTurnRight(boolean value) {
        this.turnRight = value;
        choiseAnimation();
    }

    public void setLinearVelocity(float x, float y) {
        body.setLinearVelocity(x, y);
    }

    private void choiseAnimation() {
        if(!isOnGround() && state != PlayerState.BURNING) {
            animationState = PlayerState.JUMP;
        } else {
            animationState = state;
        }
        if(turnRight) {
            animation = rigthAnimations.get(animationState);
        } else {
            animation = leftAnimations.get(animationState);
        }
    }
    
    public boolean isOnGround() {
        return numOfFootContacts > 0;
    }

    @Override
    public void act(float delta) {
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
}
