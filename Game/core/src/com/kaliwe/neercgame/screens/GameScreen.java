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
    public static boolean lock;

    public GameScreen() {
        Gdx.gl.glClearColor(135/255f,206/255f,235/255f,1);
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

        //stage = new BetweenStagesStage((short)10,(short) 10,"10");
        if (GameStateManager.next) {
            try {
                stage = GameStateManager.getNext();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        stage.act(delta);
        stage.draw();
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
