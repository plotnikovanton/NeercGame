package com.kaliwe.neercgame.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.kaliwe.neercgame.box2d.PlayerUserData;
import com.kaliwe.neercgame.enums.WalkingState;
import com.kaliwe.neercgame.utils.Constants;

/**
 * Created by anton on 18.11.15.
 */
public class Player extends GameActor {
    private boolean jumping;
    private boolean dodging;
    private WalkingState walkingState = WalkingState.STAND;

    private Animation walkRight;
    private Animation walkLeft;

    private float stateTime;

    public Player(Body body) {
        super(body);

        // TODO: move to TextureUtil
        Texture texture = new Texture("player.png");
        TextureRegion split[][] = TextureRegion.split(texture, 46, 75);
        TextureRegion mirror[][] = TextureRegion.split(texture, 46, 75);
        for (TextureRegion[] i : mirror) {
            for (TextureRegion j : i) {
                j.flip(true, false);
            }
        }

        walkRight = new Animation(0.12f, split[0]);
        walkLeft = new Animation(0.12f, mirror[0]);

        stateTime = 0f;
    }

    @Override
    public PlayerUserData getUserData() {
        return (PlayerUserData) userData;
    }

    public void jump() {
        if (!(jumping || dodging)) {
            body.applyLinearImpulse(getUserData().getJumpingLinearImpulse(), body.getWorldCenter(), true);
        }
    }

    public void jumped() {
        jumping = true;
    }

    public void landed() {
        jumping = false;
    }

    public void dodge() {
        if (!jumping) {
            body.setTransform(getUserData().getDodgePosition(), getUserData().getDodgeAngle());
            dodging = true;
        }
    }

    public void stopDodge() {
        dodging = false;
        body.setTransform(getUserData().getRunningPosition(), 0f);
    }

    public boolean isDodging() {
        return dodging;
    }

    public void setWalkingState(WalkingState state) {
        // TODO: Do it
        walkingState = state;
    }

    @Override
    public void act(float delta) {
        switch (walkingState) {
            case RIGHT:
                if (body.getLinearVelocity().x < Constants.PLAYER_MAX_SPEED) {
                    body.applyLinearImpulse(new Vector2(Constants.PLAYER_SPEED, 0), body.getLocalCenter(), true);
                }
                break;
            case LEFT:
                if (body.getLinearVelocity().x > -Constants.PLAYER_MAX_SPEED) {
                    body.applyLinearImpulse(new Vector2(-Constants.PLAYER_SPEED, 0), body.getLocalCenter(), true);
                }
                break;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        stateTime += Gdx.graphics.getDeltaTime();
        switch (walkingState) {


            case STAND:
                batch.draw(walkRight.getKeyFrame(0, true),
                        getPosition().x - Constants.PLAYER_WIDTH / 2,
                        getPosition().y - Constants.PLAYER_HEIGHT / 2,
                        Constants.PLAYER_WIDTH,
                        Constants.PLAYER_HEIGHT);
                break;
            case RIGHT:
                batch.draw(walkRight.getKeyFrame(stateTime, true),
                    getPosition().x - Constants.PLAYER_WIDTH / 2,
                    getPosition().y - Constants.PLAYER_HEIGHT / 2,
                    Constants.PLAYER_WIDTH,
                    Constants.PLAYER_HEIGHT);
                break;
            case LEFT:
                batch.draw(walkLeft.getKeyFrame(stateTime, true),
                        getPosition().x - Constants.PLAYER_WIDTH / 2,
                        getPosition().y - Constants.PLAYER_HEIGHT / 2,
                        Constants.PLAYER_WIDTH,
                        Constants.PLAYER_HEIGHT);
                break;
        }
    }

    public Vector2 getPosition() {
        return super.body.getPosition();
    }
}
