package com.kaliwe.neercgame.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.kaliwe.neercgame.screens.GameScreen;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anton on 03.12.15.
 */
public class HUDUtils {
    private static BitmapFont mainFont = ResourceUtils.getFont("visitor");
    private static DecimalFormat totalScoreFormatter = new DecimalFormat("#0.00");
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");

    public static void drawCollectedBugs(Batch sb, Camera hudCam, short current, short total) {
        sb.setProjectionMatrix(hudCam.combined);
        sb.begin();

        float xOffset = 10;
        float yOffset = 10;
        float bugSize = 12;
        sb.draw(ResourceUtils.getTextureRegion("bugHud"),
                xOffset - hudCam.viewportWidth / 2 ,
                hudCam.viewportHeight / 2 - bugSize - yOffset,
                bugSize,
                bugSize);

        mainFont.setColor(Color.BLACK);
        mainFont.draw(sb, current + "/" + total,
                bugSize + xOffset - hudCam.viewportWidth / 2 + 3,
                hudCam.viewportHeight / 2 - yOffset - bugSize / 2 + mainFont.getCapHeight() / 2);

        sb.end();
    }

    public static void drawTotalScore(Batch sb, Camera hudCam, short score, short maxScore) {
        float defaultOffsetX = 10;
        float defaultOffsetY = 10;
        drawTotalScore(sb, hudCam, hudCam.viewportWidth / 2 - defaultOffsetX,
                hudCam.viewportHeight / 2 - defaultOffsetY, score, maxScore);
    }

    public static void drawTotalScore(Batch sb, Camera hudCam, float offsetX, float offsetY, short score, short maxScore) {
        GlyphLayout layout = new GlyphLayout();

        sb.begin();
        String totalScore = totalScoreFormatter.format(GameScreen.getTotalScore() + (double) score / (double) maxScore);
        layout.setText(mainFont, totalScore);

        mainFont.setColor(Color.BLACK);
        mainFont.draw(sb,
                totalScore
                , offsetX - layout.width, offsetY);

        long totalTime = (long) (GameScreen.getTotalTime() * 1000);

        String time = simpleDateFormat.format(new Date(totalTime));
        layout.setText(mainFont, time);

        mainFont.draw(sb, time, offsetX - layout.width, offsetY - mainFont.getCapHeight() - 2);
        sb.end();
    }
}
