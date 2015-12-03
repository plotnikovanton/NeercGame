package com.kaliwe.neercgame.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by anton on 03.12.15.
 */
public class HUDUtils {
    private static BitmapFont mainFont = ResourceUtils.getFont("visitor");

    public static void drawCollectedBugs(Batch sb, Camera hudCam, short current, short total) {
        sb.setProjectionMatrix(hudCam.combined);
        sb.begin();
        mainFont.draw(sb, current + "/" + total, 0, 0);

        sb.end();
    }
}
