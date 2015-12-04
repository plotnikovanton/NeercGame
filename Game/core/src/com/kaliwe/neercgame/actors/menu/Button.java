package com.kaliwe.neercgame.actors.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kaliwe.neercgame.utils.ResourceUtils;

/**
 * Created by anton on 04.12.15.
 */
public class Button {
    private static final BitmapFont font = ResourceUtils.getFont("visitor");
    private static final Color BG_BUTTON = new Color();
    private static final float WIDTH = 100f;
    private static final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private float x;
    private float y;
    private OrthographicCamera cam;

    private boolean selected;

    public Button(OrthographicCamera camera, float y) {
        this(camera, camera.position.x - WIDTH / 2, y);
    }

    public Button(OrthographicCamera camera, float x, float y) {
        this.x = x;
        this.y = y;
        this.selected = false;
        this.cam = camera;
    }

   public void render(Batch b) {
       shapeRenderer.setProjectionMatrix(cam.combined);
       shapeRenderer.setColor(BG_BUTTON);

   }

}
