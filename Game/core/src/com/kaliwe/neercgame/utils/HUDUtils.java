package com.kaliwe.neercgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kaliwe.neercgame.screens.GameScreen;
import com.kaliwe.neercgame.stages.GameStage;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anton on 03.12.15.
 */
public class HUDUtils {
    private static BitmapFont mainFont = ResourceUtils.getFont("visitor");
    public static DecimalFormat totalScoreFormatter = new DecimalFormat("#0.00");
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();

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

        sb.setProjectionMatrix(hudCam.combined);
        sb.begin();
        String totalScore = totalScoreFormatter.format(GameScreen.getTotalScore() + (double) score / (double) maxScore);
        layout.setText(mainFont, totalScore);

        mainFont.setColor(Color.BLACK);
        mainFont.draw(sb,
                totalScore
                , offsetX - layout.width, offsetY);

        long totalTime = (long) (GameStage.getTime() * 1000);

        String time = simpleDateFormat.format(new Date(totalTime));
        layout.setText(mainFont, time);

        mainFont.draw(sb, time, offsetX - layout.width, offsetY - mainFont.getCapHeight() - 2);
        sb.end();
    }

    public static void drawTextBox(Batch sb, Camera hudCam, float alpha, TextureRegion tex) {
        // draw black box
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        float height = hudCam.viewportHeight - 80;
        //float width = hudCam.viewportWidth - 80;
        float width = height / tex.getRegionHeight() * tex.getRegionWidth();
        //shapeRenderer.setProjectionMatrix(hudCam.combined);
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //shapeRenderer.setColor(0, 0, 0, alpha);
        //shapeRenderer.rect(-width / 2, -height / 2, width, height);
        //shapeRenderer.end();
        sb.setProjectionMatrix(hudCam.combined);
        Color c = sb.getColor();
        sb.setColor(c.r, c.g, c.b, alpha);
        sb.enableBlending();
        sb.begin();
        sb.draw(tex, -width/2, -height/2, width, height);
        sb.end();

        //sb.begin();
        //mainFont.setColor(1, 1, 1, alpha);
        //mainFont.draw(sb, text, -width / 2 + 15, height / 2 - 15,
        //        width - 30, 1, true
        //);
        sb.end();
        sb.setColor(c.r, c.g, c.b, alpha);
    }
}
