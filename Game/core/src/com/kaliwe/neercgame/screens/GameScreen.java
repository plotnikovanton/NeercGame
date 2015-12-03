package com.kaliwe.neercgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.kaliwe.neercgame.stages.GameStage;
import com.kaliwe.neercgame.stages.Level0;
import com.kaliwe.neercgame.stages.Level1;
import com.kaliwe.neercgame.stages.Level2;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by anton on 18.11.15.
 */
public class GameScreen implements Screen {
    private Iterator<Class<? extends GameStage>> iter;
    private GameStage stage;
    private static double totalScore = 0;
    private static float totalTime = 0;

    public GameScreen() {
        iter = new ArrayList<Class<? extends GameStage>>() {{
            add(Level0.class);
            add(Level1.class);
            add(Level2.class);

        }}.iterator();
        try {
            setStage(iter.next());
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void setStage(Class<? extends GameStage> stage) throws IllegalAccessException, InstantiationException {
        this.stage = stage.newInstance();
        Gdx.input.setInputProcessor(this.stage);
    }

    public static double getTotalScore() {
        return totalScore;
    }

    public static float getTotalTime() {
        return totalTime;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (stage.isNext()) {
            totalScore += stage.getScore() / stage.getMaxScore();
            try {
                setStage(iter.next());
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        } else if (stage.isFailed()) {
            stage.dispose();
            try {
                setStage(stage.getClass());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        totalTime += delta;
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
