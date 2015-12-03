package com.kaliwe.neercgame.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by anton on 02.12.15.
 */
public class Background {
    private final TextureRegion tr;
    private final float xMultiplier;
    private final float yMultiplier;
    private final float width;
    private final float height;
    private final Camera camera;

    private final float offsetX;
    private final float offsetY;

    public Background(TextureRegion tr, float sizeMultiplier, float xMultiplier, float yMultiplier, Camera camera, float offsetX, float offsetY) {
        this.tr = tr;
        this.xMultiplier = xMultiplier;
        this.yMultiplier = 1 - yMultiplier;
        this.camera = camera;
        this.width = tr.getRegionWidth() * sizeMultiplier;
        this.height = tr.getRegionHeight() * sizeMultiplier;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public void draw(Batch sb, Vector3 position) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        float realPositionX = (float) (position.x - Math.floor((position.x * xMultiplier + camera.viewportWidth / 2) / width) * width / xMultiplier);
        float posX = -(realPositionX * xMultiplier - offsetX);
        float posY = -position.y + position.y * yMultiplier + offsetY;

        sb.draw(tr, posX, posY, width, height);

        if (-posX < camera.viewportWidth / 2) {
            sb.draw(tr, posX - width, posY, width, height);
        }

        sb.end();
    }
}
